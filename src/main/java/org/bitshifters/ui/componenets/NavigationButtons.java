package org.bitshifters.ui.componenets;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.layout.VBox;

public class NavigationButtons extends VBox {
    private final CustomButton battleshipButton;
    private final CustomButton ticTacToeButton;
    private final CustomButton strategoButton;
    private final CustomButton backButton;

    /** 
     * Create a new NavigationButtons object
     * @param mainFrame the main frame
     */
    public NavigationButtons(MainFrame mainFrame) {
        super(5);
        setPrefWidth(80);

        String style = ""; // defualt button style

        this.battleshipButton = new CustomButton("Battleship", getPrefWidth(), style);
        this.ticTacToeButton = new CustomButton("TicTacToe", getPrefWidth(), style);
        this.strategoButton = new CustomButton("Stratego", getPrefWidth(), style);

        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));

        getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);
    }

    public void setButtonWidth(double width) {
        setPrefWidth(width);
        battleshipButton.setPrefWidth(width);
        ticTacToeButton.setPrefWidth(width);
        strategoButton.setPrefWidth(width);
    }

    /** 
     * Set the height of the buttons
     * @param height the height of the buttons
     */
    public void setButtonHeight(double height) {
        battleshipButton.setPrefHeight(height);
        ticTacToeButton.setPrefHeight(height);
        strategoButton.setPrefHeight(height);
    }

    /** 
     * Set the size of the buttons
     * @param width the width of the buttons
     * @param height the height of the buttons
     */
    public void setButtonSize(double width, double height) {
        setButtonWidth(width);
        setButtonHeight(height);
    }

    /** 
     * Set the style of the buttons
     * @param style the style string of the buttons
     */
    public void setButtonStyle(String style) {
        battleshipButton.setStyle(style);
        ticTacToeButton.setStyle(style);
        strategoButton.setStyle(style);
    }

    /** 
     * Get the battleship button
     * @return the battleship button
     */
    public CustomButton getBattleshipButton() {
        return battleshipButton;
    }

    /** 
     * Get the tic tac toe button
     * @return the tic tac toe button
     */
    public CustomButton getTicTacToeButton() {
        return ticTacToeButton;
    }

    /** 
     * Get the stratego button
     * @return the stratego button
     */
    public CustomButton getStrategoButton() {
        return strategoButton;
    }
}
