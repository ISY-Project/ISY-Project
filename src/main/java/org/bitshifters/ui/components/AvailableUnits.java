package org.bitshifters.ui.components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;

import org.bitshifters.games.stratego.Pawns;
import org.bitshifters.games.stratego.UnitSet;
import org.bitshifters.logging.BSLogger;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public final class AvailableUnits extends HBox {
    private final BSLogger logger = new BSLogger(AvailableUnits.class);
    private UnitSet availableUnits;
    private Button[] buttons;
    private final int buttonHeight = 80;
    private final int buttonWidth = 110;
    private final VBox leftBox;
    private final VBox rightBox;
    private int selectedButtonIndex = -1;
    private final String style = "-fx-font-size: 1em; -fx-text-fill: #000000;"; // defualt button style
    private final String selectedStyle = "-fx-font-size: 1em; -fx-text-fill: #000000; -fx-background-color: #00FF00;"; // selected button style
    private final boolean isSmallVersion;

    /**
     * Constructor for the AvailableUnits
     */
    public AvailableUnits(final boolean isSmallVersion) {
        super();
        logger.debug("Creating AvailableUnits");
        this.isSmallVersion = isSmallVersion;
        int spacing = 5;

        setSpacing(spacing);
        setPrefWidth(buttonWidth*2); // set the width of the HBox to the width of the two VBoxes

        this.leftBox = new VBox(spacing);
        this.rightBox = new VBox(spacing);
        this.leftBox.setPrefWidth(buttonWidth);
        this.rightBox.setPrefWidth(buttonWidth);

        if (isSmallVersion) {
            this.availableUnits = new UnitSet().setEightUnits();
        } else {
            this.availableUnits = new UnitSet().setTenUnits();
        }
        int size = this.availableUnits.getUnits().size();
        this.buttons = new Button[size];
        createButtons();
        getChildren().addAll(leftBox, rightBox);
    }
    /**
     * Populate the buttons
     */
    private void createButtons() {
        int i = 0;

        for (final Pawns pawn : this.availableUnits.getUnits().keySet()) {
            buttons[i] = new Button();
            buttons[i].setPrefSize(buttonWidth, buttonHeight);
            buttons[i].setStyle(style);
            buttons[i].setUserData(pawn);
            buttons[i].setFocusTraversable(false);
            if (i % 2 == 0) {
                leftBox.getChildren().add(buttons[i]);
            } else {
                rightBox.getChildren().add(buttons[i]);
            }

            try {
                VBox buttonGraphic = new VBox(0); // create a VBox to hold the image and the text
                buttonGraphic.setAlignment(Pos.CENTER);

                FileInputStream input = new FileInputStream(pawn.getBluePath());
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(buttonHeight*0.9); // make the width a bit shorter to make the pawn number visible;
                imageView.setFitWidth(buttonHeight); 

                Text text = new Text(pawn.getName() + ": " + this.availableUnits.getUnits().get(pawn));
                
                buttonGraphic.getChildren().addAll(imageView, text);

                buttons[i].setGraphic(buttonGraphic);
            } catch (FileNotFoundException e) {
                logger.log(Level.WARNING, "Error loading image for pawn: {0}", pawn.toString());
                buttons[i].setText(pawn.toString() + ": " + this.availableUnits.getUnits().get(pawn));
            } catch (Exception e) {
                logger.error("Error creating button for pawn: " + pawn.toString(), e);
            }

            i++;
        }
    }

    /**
     * Remove a unit from the available units
     * @param pawn the pawn to remove
     * @return true if the unit button is empty, false otherwise
     */
    public Pawns removeUnit(Pawns pawn) {
        for (Button button : buttons) {
            Pawns buttonPawn = (Pawns) button.getUserData();
            if (buttonPawn.equals(pawn)) {
                VBox buttonContent = (VBox) button.getGraphic();
                Text text = (Text) buttonContent.getChildren().get(1);
                int amount = this.availableUnits.getUnits().get(pawn);

                amount--;
                this.availableUnits.getUnits().put(pawn, amount);

                text.setText(pawn.getName() + ": " + amount);

                if (amount == 0) {
                    button.setDisable(true);
                    selectedButtonIndex = -1;
                    button.setStyle(style);

                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i].isDisabled() == false) {
                            return selectUnitButton(i);
                        }
                    }

                    return null;
                }
                return pawn;
            }
        }
        return null;
    }

    public Pawns selectUnitButton(int index){
        logger.debug("Selecting button: " + index + ", Old button: " + selectedButtonIndex);	
        if (selectedButtonIndex == index) {
            buttons[selectedButtonIndex].setStyle(style);
            selectedButtonIndex = -1;
            return null;
        } else if (selectedButtonIndex != -1) {
            buttons[selectedButtonIndex].setStyle(style);
        } 
        buttons[index].setStyle(selectedStyle);
        selectedButtonIndex = index;
        return (Pawns) buttons[index].getUserData();
    }

    public boolean isPlacingDone() {
        for (Button button : buttons) {
            if (!button.isDisabled()) {
                return false;
            }
        }
        return true;
    }

    public UnitSet getAvailableUnits() {
        return availableUnits;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public int getButtonWidth() {
        return buttonWidth;
    }


    public VBox getLeftBox() {
        return leftBox;
    }

    public VBox getRightBox() {
        return rightBox;
    }

    public void reset() {
        this.leftBox.getChildren().clear();
        this.rightBox.getChildren().clear();
        if (this.isSmallVersion) {
            this.availableUnits = new UnitSet().setEightUnits();
        } else {
            this.availableUnits = new UnitSet().setTenUnits();
        }
        int size = this.availableUnits.getUnits().size();
        this.buttons = new Button[size];
        createButtons();

        selectedButtonIndex = -1;
    }

}
