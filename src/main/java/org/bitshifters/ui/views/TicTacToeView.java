package org.bitshifters.ui.views;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.BaseGrid;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicTacToeView extends BorderPane {
    private final BaseGrid baseGrid;

    public BaseGrid getBaseGrid() {
        return this.baseGrid;
    }

    public TicTacToeView(MainFrame mainFrame) {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        this.baseGrid = new BaseGrid(3);
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
        for (Button[] row : buttonGrid) {
            for (Button cell : row) {
                cell.setMinSize(50, 50);
                cell.setMaxSize(50, 50);
                cell.setStyle("-fx-font-size: 20");
                cell.setOnAction(_ -> {
                    if (cell.getText().isEmpty()) {
                        cell.setText("X");
                    } else {
                        cell.setText("O");
                    }
                });
            }
        }
    }
}
