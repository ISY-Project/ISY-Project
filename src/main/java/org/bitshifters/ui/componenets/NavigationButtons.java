package org.bitshifters.ui.componenets;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class NavigationButtons extends HBox {
    private final Button battleshipButton = new Button("Battleship");
    private final Button ticTacToeButton = new Button("Tic Tac Toe");
    private final Button strategoButton = new Button("Stratego");

    public NavigationButtons(MainFrame mainFrame) {
        super();
        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));
        getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);
    }

    public Button getBattleshipbutton() {
        return battleshipButton;
    }

    public Button getTictactoebutton() {
        return ticTacToeButton;
    }

    public Button getStrategobutton() {
        return strategoButton;
    }
}
