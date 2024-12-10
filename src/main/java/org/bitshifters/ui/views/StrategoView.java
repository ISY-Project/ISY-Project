package org.bitshifters.ui.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bitshifters.games.stratego.Pawns;
import org.bitshifters.games.stratego.UnitSet;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.BaseGrid;
import org.bitshifters.ui.componenets.NavigationButtons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StrategoView extends BorderPane {
    private static final BSLogger logger = new BSLogger(StrategoView.class);
    private final NavigationButtons hButtonBox;
    private final BaseGrid baseGrid;
    private UnitSet units;
    private int buttonSize = 80;
    private boolean isSmallVersion = false;

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
        this.isSmallVersion = smallVerison;
        HBox hGridBox = new HBox();
        VBox vBox = new VBox();
        hButtonBox = new NavigationButtons(mainFrame, true, false);
        
        if (smallVerison) {
            this.units = UnitSet.EIGHT;
            this.baseGrid = new BaseGrid(8);
        } else {
            this.baseGrid = new BaseGrid(10);
            this.units = UnitSet.TEN;
        }

        fillGrid();

        hGridBox.getChildren().addAll(this.baseGrid);
        hGridBox.setAlignment(javafx.geometry.Pos.CENTER);

        vBox.getChildren().addAll(hGridBox);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        this.setCenter(vBox);
        this.setRight(hButtonBox);
    }

    /** 
     * Get the base grid
     * @return the base grid object with the buttons
     */
    public BaseGrid getBaseGrid() {
        return this.baseGrid;
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
            Pawns fromPawn = (Pawns) fromButton.getUserData();
            if (fromPawn == null) {
                logger.error("No pawn to move from button: " + fromButton);
                return;
            }
            updateButton(toButton, fromPawn);
            updateButton(fromButton, null);
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
    public final void updateButton(int row, int col, Pawns pawn) {
        this.updateButton(getButton(row, col), pawn);
    }

    /** 
     * Update the specified button with the specified cell
     * @param button the button to update
     * @param pawn the pawn to update the button with
     */
    public final void updateButton(Button button, Pawns pawn) {
        try {
            FileInputStream input = null;
            if (pawn == null) {
                button.setGraphic(null);
                button.setText("");
                button.setStyle("-fx-background-color: #aaddaa");
            } else switch (pawn) {
                case UNKNOWN -> input = new FileInputStream("src\\main\\resources\\images\\StrategoRed.png");
                case LAKE -> button.setStyle("-fx-background-color: #0e87a6");
                default -> input = new FileInputStream(pawn.getBluePath());
            }
            if (input != null) {
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(buttonSize);
                imageView.setFitWidth(buttonSize*1.10); // make the width a bit wider to make the pawn number visible
                button.setGraphic(imageView);
            }
        } catch (FileNotFoundException e) {
            button.setText(pawn.toString());
            logger.error("Error loading image", e);
        }
        button.setUserData(pawn); // store the pawn type in the button
    }

    /** 
     * Fill the grid with the pawns
     */
    public final void fillGrid() {
        logger.debug("Filling grid");
        Random rand = new Random();

        if (isSmallVersion) {
            this.buttonSize = 100;
        } else {
            this.buttonSize = 80;
        }

        HashMap<Pawns, Integer> unitCount = units.getUnits();
        ArrayList<Pawns> pawns = new ArrayList<>();

        unitCount.entrySet().forEach(entry -> {
            for (int i = 0; i < entry.getValue(); i++) {
                pawns.add(entry.getKey());
            }
        });
        if (isSmallVersion && pawns.size() != this.baseGrid.getGridWidth()*3) { 
            for (int i=0; i<this.baseGrid.getGridWidth()*3-pawns.size(); i++) {
                pawns.add(null);
            }
        }

        Button[][] buttonGrid = this.baseGrid.getButtonGrid();
        try {
            if (!isSmallVersion) {
                FileInputStream inputRed = new FileInputStream("src\\main\\resources\\images\\StrategoRed.png");
                Image imageRed = new Image(inputRed);
                for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
                    for (int col = 0; col < this.baseGrid.getGridHeight(); col++) {
                        Button button = buttonGrid[row][col];
                        button.setStyle("-fx-background-color: #aaddaa");
                        if (row >= 6 && row <= 9) { // blue side (bottom)
                            Pawns pawn = pawns.get(rand.nextInt(pawns.size()));
                            pawns.remove(pawn);
                            if (pawn == null) {
                                button.setGraphic(null);
                                button.setText("");
                            } else {
                                FileInputStream inputBlue = new FileInputStream(pawn.getBluePath());
                                Image imageBlue = new Image(inputBlue);

                                ImageView imageView = new ImageView(imageBlue);
                                imageView.setFitHeight(buttonSize);
                                imageView.setFitWidth(buttonSize*1.10); // make the width a bit wider to make the pawn number visible
                                button.setGraphic(imageView);
                            }
                            button.setUserData(pawn); // store the pawn type in the button
                        } else if (row >= 0 && row <= 2) { // red side (top)
                            ImageView imageView = new ImageView(imageRed);
                            imageView.setFitHeight(buttonSize);
                            imageView.setFitWidth(buttonSize*1.10); // match the width of the blue side
                            button.setGraphic(imageView);
                            button.setUserData(Pawns.UNKNOWN); // store NONE in the button for the red side
                        } else if (col == 2) { // lake tiles
                            button.setStyle("-fx-background-color: #0e87a6");
                            button.setUserData(Pawns.LAKE); // store LAKE in the button for the lake tiles
                        } else if (col == 6) { // lake tiles
                            button.setStyle("-fx-background-color: #0e87a6");
                            button.setUserData(Pawns.LAKE); // store LAKE in the button for the lake tiles
                        } else {
                            button.setUserData(null); // store NONE in the button for the empty tiles
                        }
                        button.setMinSize(buttonSize+20, buttonSize); // +20 to make the buttons wider to accommodate the pawn number
                        button.setMaxSize(buttonSize+20, buttonSize);
                        button.setOnAction(_ -> {
                            // button.setStyle("-fx-background-color: #ff0000");
                        });
                        if (button.getUserData() != null && button.getUserData() != null) {
                            logger.debug("button: " + row + "," + col + " " + "Pawn: " + button.getUserData());
                        }
                    }
                }
            } else {
                FileInputStream inputRed = new FileInputStream("src\\main\\resources\\images\\StrategoRed.png");
                Image imageRed = new Image(inputRed);
                for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
                    for (int col = 0; col < this.baseGrid.getGridHeight(); col++) {
                        Button button = buttonGrid[row][col];
                        button.setStyle("-fx-background-color: #aaddaa");
                        if (row >= 6 && row <= 9) { // blue side (bottom)
                            Pawns pawn = pawns.get(rand.nextInt(pawns.size()));
                            pawns.remove(pawn);
                            if (pawn == null) {
                                button.setGraphic(null);
                                button.setText("");
                            } else {
                                FileInputStream inputBlue = new FileInputStream(pawn.getBluePath());
                                Image imageBlue = new Image(inputBlue);

                                ImageView imageView = new ImageView(imageBlue);
                                imageView.setFitHeight(buttonSize);
                                imageView.setFitWidth(buttonSize*1.10); // make the width a bit wider to make the pawn number visible
                                button.setGraphic(imageView);
                            }
                            button.setUserData(pawn); // store the pawn type in the button
                        } else if (row >= 0 && row <= 3) { // red side (top)
                            ImageView imageView = new ImageView(imageRed);
                            imageView.setFitHeight(buttonSize);
                            imageView.setFitWidth(buttonSize*1.10); // match the width of the blue side
                            button.setGraphic(imageView);
                            button.setUserData(Pawns.UNKNOWN); // store NONE in the button for the red side
                        } else if (col >= 2 && col <= 3) { // lake tiles
                            button.setStyle("-fx-background-color: #0e87a6");
                            button.setUserData(Pawns.LAKE); // store LAKE in the button for the lake tiles
                        } else if (col >= 6 && col <= 7) { // lake tiles
                            button.setStyle("-fx-background-color: #0e87a6");
                            button.setUserData(Pawns.LAKE); // store LAKE in the button for the lake tiles
                        } else {
                            button.setUserData(null); // store NONE in the button for the empty tiles
                        }
                        button.setMinSize(buttonSize+20, buttonSize); // +20 to make the buttons wider to accommodate the pawn number
                        button.setMaxSize(buttonSize+20, buttonSize);
                        button.setOnAction(_ -> {
                            // button.setStyle("-fx-background-color: #ff0000");
                        });
                        if (button.getUserData() != null && button.getUserData() != null) {
                            logger.debug("button: " + row + "," + col + " " + "Pawn: " + button.getUserData());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Pawn image file not found: " + e);
            logger.debug("Pawn image file not found, setting empty buttons");
            for (Button[] row : buttonGrid) {
                for (Button cell : row) {
                    cell.setStyle("-fx-background-color: #aaddaa");
                    cell.setMinSize(buttonSize, buttonSize);
                    cell.setMaxSize(buttonSize, buttonSize);
                    cell.setOnAction(_ -> {
                        // cell.setStyle("-fx-background-color: #ff0000");
                    });
                }
            }
        } catch (Exception e) {
            logger.error("Error filling grid", e);
            logger.debug("Error filling grid, setting empty buttons" + pawns.size());
        }
    }
}
