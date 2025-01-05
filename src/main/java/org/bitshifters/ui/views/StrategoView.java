package org.bitshifters.ui.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.bitshifters.games.stratego.Pawns;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.components.AvailableUnits;
import org.bitshifters.ui.components.BaseGrid;
import org.bitshifters.ui.components.CustomBorderPane;
import org.bitshifters.ui.components.NavigationButtons;
import org.bitshifters.ui.components.PawnButtonInformation;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StrategoView extends CustomBorderPane {
    private static final BSLogger logger = new BSLogger(StrategoView.class);
    private final NavigationButtons hButtonBox;
    private final AvailableUnits availableUnitsButtons;
    private final BaseGrid baseGrid;
    private int buttonSize = 75;
    private boolean isSmallVersion = false;
    private static String style = "-fx-background-color: #aaddaa00"; // transparent background
    private ImageView gridBackground = new ImageView();

    /** 
     * Constructor for the StrategoView with the default size (10*10)
     * @param mainFrame the main frame object
     */
    public StrategoView(MainFrame mainFrame) {
        this(mainFrame, false);
    }

    /** 
     * Constructor for the StrategoView
     * @param mainFrame the main frame object
     * @param smallVerison if set true, the small version (8*8) of the view will be created
     */
    public StrategoView(MainFrame mainFrame, boolean smallVerison) {
        super(mainFrame);
        logger.debug("Creating StrategoView");
        this.isSmallVersion = smallVerison;
        StackPane gridStackPane = new StackPane();
        VBox vBox = new VBox();
        hButtonBox = new NavigationButtons(mainFrame, true, false);
        hButtonBox.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        availableUnitsButtons = new AvailableUnits(smallVerison);
        
        VBox rightBox = new VBox(20);
        rightBox.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        rightBox.getChildren().add(hButtonBox);
        rightBox.getChildren().add(availableUnitsButtons);

        if (smallVerison) {
            this.baseGrid = new BaseGrid(8);
        } else {
            this.baseGrid = new BaseGrid(10);
        }

        Image image = null;
        try {
            if (smallVerison) {
                logger.critical("Loading small version of the board");
                image = new Image("file:src/main/resources/images/StrategoBoard8.png");
            } else {
                image = new Image("file:src/main/resources/images/StrategoBoard10.png");
            }
        } catch (Exception e) {
            logger.error("Error loading stratego background image(s)", e);
        }

        fillGrid();

        if (image != null) {
            gridBackground.setImage(image);
            gridBackground.setFitHeight(buttonSize * baseGrid.getGridHeight() * 1.013);
            gridBackground.setFitWidth(buttonSize * baseGrid.getGridWidth() * 1.21); // *1.10 to make the buttons wider to accommodate the pawn number

            gridStackPane.getChildren().addAll(this.gridBackground, this.baseGrid);
        } else {
            gridStackPane.getChildren().add(this.baseGrid);
        }

        gridStackPane.setAlignment(javafx.geometry.Pos.CENTER);

        vBox.getChildren().addAll(gridStackPane);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        this.setCenter(vBox);
        this.setRight(rightBox);
        setPlacingMode(true);
    }

    /** 
     * Get the base grid
     * @return the base grid object with the buttons
     */
    public BaseGrid getBaseGrid() {
        return this.baseGrid;
    }

    /** 
     * Get the available units buttons
     * @return the available units buttons object
     */
    public AvailableUnits getAvailableUnitsButtons() {
        return this.availableUnitsButtons;
    }

    /** 
     * Get the button at the specified row and column
     * @param row the row number of the button
     * @param col the column number of the button
     * @return the button at the specified row and column
     */
    public Button getButton(int row, int col) {
        return this.baseGrid.getButtonGrid()[row][col];
    }

    /** 
     * Get the button at the specified row and column
     * @param fromRow the row number of the button to move the pawn from
     * @param fromCol the column number of the button to move the pawn from
     * @param toRow the row number of the button to move the pawn to
     * @param toCol the column number of the button to move the pawn to
     */
    public final void movePawn(int fromRow, int fromCol, int toRow, int toCol) {
        movePawn(getButton(fromRow, fromCol), getButton(toRow, toCol));
    }

    /** 
     * Move the pawn from the fromButton to the toButton
     * @param fromButton the button to move the pawn from
     * @param toButton the button to move the pawn to
     */
    public final void movePawn(Button fromButton, Button toButton) {
        try {
            PawnButtonInformation fromInformation = (PawnButtonInformation) fromButton.getUserData();
            if (fromInformation.pawn() == null) {
                logger.error("No pawn to move from button: " + fromButton);
                return;
            }
            updateButton(toButton, fromInformation);
            updateButton(fromButton, new PawnButtonInformation(null, false));
        } catch (Exception e) {
            logger.error("Error moving pawn", e);
        }
    }

    /** 
     * Update the button at the specified row and column with the specified cell
     * @param row the row number of the button
     * @param col the column number of the button
     * @param pawn the pawn to update the button with
     */
    public final void updateButton(int row, int col, PawnButtonInformation pawn) {
        this.updateButton(getButton(row, col), pawn);
    }

    public final void updateButton(Button button, PawnButtonInformation pawn) {
        updateButton(button, pawn.pawn(), pawn.isOpponent());
    }

    /** 
     * Update the specified button with the specified cell
     * @param button the button to update
     * @param pawn the pawn to update the button with
     */
    public final void updateButton(Button button, Pawns pawn, boolean isOpponent) {
        try {
            FileInputStream input = null;
            if (pawn == null) {
                button.setGraphic(null);
                button.setText("");
                button.setStyle(style);
            } else switch (pawn) {
                case UNKNOWN -> input = new FileInputStream("src\\main\\resources\\images\\StrategoRed.png");
                case LAKE -> button.setStyle(style);
                default -> {
                    if (isOpponent) {
                        input = new FileInputStream(pawn.getRedPath());
                    } else {
                        input = new FileInputStream(pawn.getBluePath());
                    }
                }
            }
            if (input != null) {
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(buttonSize);
                imageView.setFitWidth(buttonSize*1.10); // make the width a bit wider to make the pawn number visible
                Platform.runLater(() -> button.setGraphic(imageView));}
        } catch (FileNotFoundException e) {
            button.setText(pawn.toString());
            logger.error("Error loading image", e);
        }
        button.setUserData(new PawnButtonInformation(pawn, isOpponent)); // store the pawn type in the button
    }

    /** 
     * Fill the grid with the pawns
     */
    public final void fillGrid() {
        logger.debug("Filling grid");

        if (isSmallVersion) {
            this.buttonSize = 95;
        } else {
            this.buttonSize = 75;
        }

        Button[][] buttonGrid = this.baseGrid.getButtonGrid();
        if (!isSmallVersion) {
            for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
                for (int col = 0; col < this.baseGrid.getGridHeight(); col++) {
                    Button button = buttonGrid[row][col];
                    button.setStyle(style);
                    if ((row >= 4 && row <= 5)) { // lake tiles
                        if ((col >= 2 && col <= 3) || (col >= 6 && col <= 7)) { // lake tiles
                            updateButton(button, Pawns.LAKE, true); // lake tiles are always opponent
                        }
                    }
                    button.setMinSize(buttonSize*1.20, buttonSize); // *1.10 to make the buttons wider to accommodate the pawn number
                    button.setMaxSize(buttonSize*1.20, buttonSize);
                }
            }
        } else {
            for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
                for (int col = 0; col < this.baseGrid.getGridWidth(); col++) {
                    Button button = buttonGrid[row][col];
                    button.setStyle(style);
                    if ((row >= 3 && row <= 4)) { // lake tiles
                        if ((col == 2) || (col == 5)) { // lake tiles
                            updateButton(button, Pawns.LAKE, true); // lake tiles are always opponent
                        }
                    }
                    button.setMinSize(buttonSize*1.20, buttonSize); // *1.10 to make the buttons wider to accommodate the pawn number
                    button.setMaxSize(buttonSize*1.20, buttonSize);
                }
            }
        }
    }

    public final void setPlacingMode(boolean placingMode) {
        // TODO: add an option to diable all other buttons when placing mode is enabled
        // this.availableUnitsButtons.setPlacingMode(placingMode);
        this.baseGrid.setPlacingMode(placingMode);
    }

    public final boolean isPlacingDone() {
        return this.availableUnitsButtons.isPlacingDone();
    }

    public boolean isSmallVersion() {
        return this.isSmallVersion;
    }

    public static String getButtonStyle() {
        return style;
    }
}
