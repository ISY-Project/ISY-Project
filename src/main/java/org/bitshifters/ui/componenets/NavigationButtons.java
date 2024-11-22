package org.bitshifters.ui.componenets;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.layout.VBox;

public class NavigationButtons extends VBox {
    private final CustomButton battleshipButton;
    private final CustomButton ticTacToeButton;
    private final CustomButton strategoButton;

    public NavigationButtons(MainFrame mainFrame) {
        super(5);
        setPrefWidth(80);

        String style = ""; // button style

        this.battleshipButton = new CustomButton("Battleship", getPrefWidth(), style);
        this.ticTacToeButton = new CustomButton("TicTacToe", getPrefWidth(), style);
        this.strategoButton = new CustomButton("Stratego", getPrefWidth(), style);

        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));

        getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);
    }

    public void setButtonWidth(double width) {
        battleshipButton.setPrefWidth(width);
        ticTacToeButton.setPrefWidth(width);
        strategoButton.setPrefWidth(width);
    }

    public void setButtonHeight(double height) {
        battleshipButton.setPrefHeight(height);
        ticTacToeButton.setPrefHeight(height);
        strategoButton.setPrefHeight(height);
    }

    public void setButtonSize(double width, double height) {
        setButtonWidth(width);
        setButtonHeight(height);
    }

    public void setButtonStyle(String style) {
        battleshipButton.setStyle(style);
        ticTacToeButton.setStyle(style);
        strategoButton.setStyle(style);
    }

    public CustomButton getBattleshipbutton() {
        return battleshipButton;
    }

    public CustomButton getTictactoebutton() {
        return ticTacToeButton;
    }

    public CustomButton getStrategobutton() {
        return strategoButton;
    }
}
