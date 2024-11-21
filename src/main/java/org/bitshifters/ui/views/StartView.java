package org.bitshifters.ui.views;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StartView extends BorderPane {
    private final Button battleshipButton;
    private final Button ticTacToeButton;
    private final Button strategoButton;

    public StartView(MainFrame mainFrame) {
        this.battleshipButton = new Button("Battleship");
        this.ticTacToeButton = new Button("Tic Tac Toe");
        this.strategoButton = new Button("Stratego");
        HBox hBox = new HBox();

        this.battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        this.ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        this.strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));

        hBox.getChildren().addAll(this.battleshipButton, this.ticTacToeButton, this.strategoButton);

        this.setCenter(hBox);
    }

    public Button getBattleshipButton() {
        return this.battleshipButton;
    }

    public Button getTicTacToeButton() {
        return this.ticTacToeButton;
    }

    public Button getStrategoButton() {
        return this.strategoButton;
    }
}
