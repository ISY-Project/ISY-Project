package org.bitshifters.ui.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.BaseGrid;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StrategoView extends BorderPane {
    private final BaseGrid baseGrid;

    public StrategoView(MainFrame mainFrame) {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        this.baseGrid = new BaseGrid(10);
        hBox.getChildren().addAll(this.baseGrid);

        fillGrid();

        Button battleshipButton = new Button("Battleship");
        Button ticTacToeButton = new Button("Tic Tac Toe");
        Button strategoButton = new Button("Stratego");
        HBox hBox2 = new HBox();

        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));

        hBox.getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);

        vBox.getChildren().addAll(hBox, hBox2);
        this.setCenter(vBox);
    }

    public final void fillGrid() {
        Button[][] buttonGrid = this.baseGrid.getButtonGrid();
        try {
            FileInputStream inputBlue = new FileInputStream("src\\main\\resources\\StrategoBlue.png");
            FileInputStream inputRed = new FileInputStream("src\\main\\resources\\StrategoRed.png");
            Image imageBlue = new Image(inputBlue);
            Image imageRed = new Image(inputRed);
            for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
                for (int col = 0; col < this.baseGrid.getGridHeight(); col++) {
                    Button button = buttonGrid[row][col];
                    if (row >= 0 && row <= 3) {
                        ImageView imageView = new ImageView(imageBlue);
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        button.setGraphic(imageView);
                    } else if (row >= this.baseGrid.getGridHeight()-4 && row <= this.baseGrid.getGridHeight()-1) {
                        ImageView imageView = new ImageView(imageRed);
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        button.setGraphic(imageView);
                    } else if (col >= 2 && col <= 3) {
                        button.setStyle("-fx-background-color: #aaaadd");
                    } else if (col >= 6 && col <= 7) {
                        button.setStyle("-fx-background-color: #aaaadd");
                    }
                    button.setStyle("-fx-background-color: #aaddaa");
                    button.setMinSize(40, 40);
                    button.setMaxSize(40, 40);
                    button.setOnAction(_ -> {
                        button.setStyle("-fx-background-color: #ff0000");
                    });
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File 'src/main/resources/StrategoRed.png' not found ");
            for (Button[] row : buttonGrid) {
                for (Button cell : row) {
                    cell.setStyle("-fx-background-color: #0000FF");
                    cell.setMinSize(40, 40);
                    cell.setMaxSize(40, 40);
                    cell.setOnAction(_ -> {
                        cell.setStyle("-fx-background-color: #ff0000");
                    });
                }
            }
        }
    }

    public BaseGrid getBaseGrid() {
        return this.baseGrid;
    }
}
