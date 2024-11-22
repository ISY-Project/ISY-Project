package org.bitshifters.ui.componenets;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class NavigationButtons extends HBox {
    private static final Button battleshipButton = new Button("Battleship");
    private static final Button ticTacToeButton = new Button("Tic Tac Toe");
    private static final Button strategoButton = new Button("Stratego");

    public NavigationButtons(MainFrame mainFrame) {
        super();
        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));
        getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);
    }

    public static Button getBattleshipbutton() {
        return battleshipButton;
    }

    public static Button getTictactoebutton() {
        return ticTacToeButton;
    }

    public static Button getStrategobutton() {
        return strategoButton;
    }
}
