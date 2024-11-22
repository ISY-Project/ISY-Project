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

public class BattleshipsView extends BorderPane {
    private static final int GRIDSIZE = 8;
    private final BaseGrid playerGrid;
    private final BaseGrid opponentGrid;

    public BattleshipsView(MainFrame mainFrame) {
        HBox hGridBox = new HBox();
        VBox vBox = new VBox();
        HBox hButtonBox = new HBox();

        this.playerGrid = new BaseGrid(GRIDSIZE);
        this.opponentGrid = new BaseGrid(GRIDSIZE);

        fillGrid();

        Button battleshipButton = new Button("Battleship");
        Button ticTacToeButton = new Button("Tic Tac Toe");
        Button strategoButton = new Button("Stratego");

        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));

        hGridBox.getChildren().addAll(this.playerGrid, this.opponentGrid);
        hButtonBox.getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);

        vBox.getChildren().addAll(hGridBox, hButtonBox);

        this.setCenter(vBox);
    }

    public final void fillGrid() {
        int buttonSize = 70;
        Button[][] buttonGrid = this.playerGrid.getButtonGrid();
        try {
            FileInputStream inputFront = new FileInputStream("src\\main\\resources\\images\\shipFront.png");
            FileInputStream inputMiddle = new FileInputStream("src\\main\\resources\\images\\shipMiddle.png");
            FileInputStream inputBack = new FileInputStream("src\\main\\resources\\images\\shipBack.png");
            Image imageFront = new Image(inputFront);
            Image imageMiddle = new Image(inputMiddle);
            Image imageBack = new Image(inputBack);
            for (int row = 0; row < this.playerGrid.getGridHeight(); row++) {
                for (int col = 0; col < this.playerGrid.getGridHeight(); col++) {
                    Button button = buttonGrid[row][col];
                    button.setStyle("-fx-background-color: #0e87a6");
                    switch (row) {
                        case 0 ->                             {
                                ImageView imageView = new ImageView(imageBack);
                                imageView.rotateProperty().set(90);
                                imageView.setFitHeight(buttonSize);
                                imageView.setFitWidth(buttonSize);
                                button.setGraphic(imageView);
                            }
                        case 1 ->                             {
                                ImageView imageView = new ImageView(imageMiddle);
                                imageView.rotateProperty().set(90);
                                imageView.setFitHeight(buttonSize);
                                imageView.setFitWidth(buttonSize);
                                button.setGraphic(imageView);
                            }
                        case 2 ->                             {
                                ImageView imageView = new ImageView(imageMiddle);
                                imageView.rotateProperty().set(90);
                                imageView.setFitHeight(buttonSize);
                                imageView.setFitWidth(buttonSize);
                                button.setGraphic(imageView);
                            }
                        case 3 ->                             {
                                ImageView imageView = new ImageView(imageFront);
                                imageView.rotateProperty().set(90);
                                imageView.setFitHeight(buttonSize);
                                imageView.setFitWidth(buttonSize);
                                button.setGraphic(imageView);
                            }
                        default -> button.setStyle("-fx-background-color: #0e87a6");
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
        buttonGrid = this.opponentGrid.getButtonGrid();
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

    public BaseGrid getPlayerGrid() {
        return this.playerGrid;
    }

    public BaseGrid getOpponentGrid() {
        return this.opponentGrid;
    }
}
