package org.bitshifters.ui.views;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.BaseGrid;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BattleshipsView extends BorderPane {
    private static final int GRIDSIZE = 8;
    private final BaseGrid playerGrid;
    private final BaseGrid opponentGrid;

    public BattleshipsView(MainFrame mainFrame) {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        this.playerGrid = new BaseGrid(GRIDSIZE);
        this.opponentGrid = new BaseGrid(GRIDSIZE);
        hBox.getChildren().addAll(this.playerGrid, this.opponentGrid);

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
        Button[][] buttonGrid = this.playerGrid.getButtonGrid();
        for (Button[] row : buttonGrid) {
            for (Button cell : row) {
                cell.setMinSize(40, 40);
                cell.setMaxSize(40, 40);
                cell.setStyle("-fx-font-size: 20");
                // cell.setOnAction(_ -> {
                //     if (cell.getText().isEmpty()) {
                //         cell.setText("X");
                //     } else {
                //         cell.setText("O");
                //     }
                // });
            }
        }

        buttonGrid = this.opponentGrid.getButtonGrid();
        for (Button[] row : buttonGrid) {
            for (Button cell : row) {
                cell.setMinSize(40, 40);
                cell.setMaxSize(40, 40);
                cell.setStyle("-fx-font-size: 20");
                // cell.setOnAction(_ -> {
                //     if (cell.getText().isEmpty()) {
                //         cell.setText("X");
                //     } else {
                //         cell.setText("O");
                //     }
                // });
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
