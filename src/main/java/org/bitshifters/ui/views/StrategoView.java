package org.bitshifters.ui.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.BaseGrid;
import org.bitshifters.ui.componenets.NavigationButtons;
import org.bitshifters.ui.enums.Pawns;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StrategoView extends BorderPane {
    private final BaseGrid baseGrid;

    public StrategoView(MainFrame mainFrame) {
        HBox hGridBox = new HBox();
        VBox vBox = new VBox();
        HBox hButtonBox = new NavigationButtons(mainFrame);
        
        this.baseGrid = new BaseGrid(10);

        fillGrid();

        hGridBox.getChildren().addAll(this.baseGrid);
        vBox.getChildren().addAll(hGridBox, hButtonBox);

        this.setCenter(vBox);
    }

    public final void fillGrid() {
        Random rand = new Random();
        ArrayList<Pawns> pawns = new ArrayList<>();
        for (Pawns pawn : Pawns.values()) {
            for (int i = 0; i < pawn.getAmount(); i++) {
                pawns.add(pawn);
            }
        }

        int buttonSize = 70;
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
                        imageView.setFitWidth(buttonSize);
                        button.setGraphic(imageView);
                    } else if (row >= 6 && row <= 9) {
                        ImageView imageView = new ImageView(imageRed);
                        imageView.setFitHeight(buttonSize);
                        imageView.setFitWidth(buttonSize);
                        button.setGraphic(imageView);
                    } else if (col >= 2 && col <= 3) {
                        button.setStyle("-fx-background-color: #aaaadd");
                    } else if (col >= 6 && col <= 7) {
                        button.setStyle("-fx-background-color: #aaaadd");
                    }
                    button.setMinSize(buttonSize, buttonSize);
                    button.setMaxSize(buttonSize, buttonSize);
                    button.setOnAction(_ -> {
                        // button.setStyle("-fx-background-color: #ff0000");
                    });
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
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
