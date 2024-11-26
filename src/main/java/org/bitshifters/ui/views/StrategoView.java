package org.bitshifters.ui.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import org.bitshifters.enums.Pawns;
import org.bitshifters.logging.BsLogger;
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
    private static final BsLogger logger = new BsLogger(StrategoView.class);
    private final BaseGrid baseGrid;

    public StrategoView(MainFrame mainFrame) {
        HBox hGridBox = new HBox();
        VBox vBox = new VBox();
        VBox hButtonBox = new NavigationButtons(mainFrame, true, false);
        
        this.baseGrid = new BaseGrid(10);

        fillGrid();

        hGridBox.getChildren().addAll(this.baseGrid);
        hGridBox.setAlignment(javafx.geometry.Pos.CENTER);

        vBox.getChildren().addAll(hGridBox);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        this.setCenter(vBox);
        this.setRight(hButtonBox);
    }

    public final void fillGrid() {
        logger.debug("Filling grid");
        Random rand = new Random();
        ArrayList<Pawns> pawns = new ArrayList<>();
        for (Pawns pawn : Pawns.values()) {
            for (int i = 0; i < pawn.getAmount(); i++) {
                pawns.add(pawn);
            }
        }

        int buttonSize = 80;
        Button[][] buttonGrid = this.baseGrid.getButtonGrid();
        try {
            FileInputStream inputRed = new FileInputStream("src\\main\\resources\\images\\StrategoRed.png");
            Image imageRed = new Image(inputRed);
            for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
                for (int col = 0; col < this.baseGrid.getGridHeight(); col++) {
                    Button button = buttonGrid[row][col];
                    button.setStyle("-fx-background-color: #aaddaa");
                    if (row >= 0 && row <= 3) {
                        Pawns pawn = pawns.get(rand.nextInt(pawns.size()));
                        pawns.remove(pawn);

                        FileInputStream inputBlue = new FileInputStream(pawn.getBluePath());
                        Image imageBlue = new Image(inputBlue);

                        ImageView imageView = new ImageView(imageBlue);
                        imageView.setFitHeight(buttonSize);
                        imageView.setFitWidth(buttonSize*1.10); // make the width a bit wider to make the pawn number visible
                        button.setGraphic(imageView);
                        button.setUserData(pawn); // store the pawn type in the button
                    } else if (row >= 6 && row <= 9) {
                        ImageView imageView = new ImageView(imageRed);
                        imageView.setFitHeight(buttonSize);
                        imageView.setFitWidth(buttonSize*1.10); // match the width of the blue side
                        button.setGraphic(imageView);
                        button.setUserData(Pawns.NONE); // store NONE in the button for the red side
                    } else if (col >= 2 && col <= 3) {
                        button.setStyle("-fx-background-color: #0e87a6");
                        button.setUserData(Pawns.LAKE); // store LAKE in the button for the lake tiles
                    } else if (col >= 6 && col <= 7) {
                        button.setStyle("-fx-background-color: #0e87a6");
                        button.setUserData(Pawns.LAKE); // store LAKE in the button for the lake tiles
                    }
                    button.setMinSize(buttonSize+20, buttonSize); // +20 to make the buttons wider to accommodate the pawn number
                    button.setMaxSize(buttonSize+20, buttonSize);
                    button.setOnAction(_ -> {
                        // button.setStyle("-fx-background-color: #ff0000");
                    });
                    if (button.getUserData() != null && button.getUserData() != Pawns.NONE) {
                        logger.debug("button: " + row + "," + col + " " + "Pawn: " + button.getUserData());
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
        }
    }

    public BaseGrid getBaseGrid() {
        return this.baseGrid;
    }
}
