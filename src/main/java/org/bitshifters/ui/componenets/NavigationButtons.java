package org.bitshifters.ui.componenets;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationButtons extends VBox {
    private final CustomButton battleshipButton;
    private final CustomButton ticTacToeButton;
    private final CustomButton strategoButton;
    private final CustomButton backButton;
    private final Boolean isHorizontal;

    public NavigationButtons(MainFrame mainFrame, Boolean enableBackButton, Boolean isHorizontal) {
        super(5);
        setPrefWidth(80);
        this.isHorizontal = isHorizontal;

        String style = ""; // defualt button style

        this.battleshipButton = new CustomButton("Battleship", getPrefWidth(), style);
        this.ticTacToeButton = new CustomButton("TicTacToe", getPrefWidth(), style);
        this.strategoButton = new CustomButton("Stratego", getPrefWidth(), style);

        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGO));

        if (enableBackButton) {
            this.backButton = new CustomButton("Back", getPrefWidth(), style);
            backButton.setOnAction(_ -> mainFrame.showScreen(Screens.START_SCREEN));
        } else {
            this.backButton = null;
        }

        if (isHorizontal) {
            HBox hBox = new HBox();
            hBox.setSpacing(5);
            hBox.getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);
            if (backButton != null) {
                hBox.getChildren().add(backButton);
            }
            getChildren().add(hBox);
        } else {
            if (backButton != null) {
                getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton, backButton);
            } else {
                getChildren().addAll(battleshipButton, ticTacToeButton, strategoButton);
            }
        }
    }

    /** 
     * Set the width of the buttons
     * @param width the width of the buttons
     */
    public void setButtonWidth(double width) {
        if (isHorizontal) {
            setPrefWidth(width*3);
            battleshipButton.setPrefWidth(width);
            ticTacToeButton.setPrefWidth(width);
            strategoButton.setPrefWidth(width);
            if (backButton != null) {
                setPrefWidth(width*4);
                backButton.setPrefWidth(width);
            }
        } else {
            setPrefWidth(width);
            battleshipButton.setPrefWidth(width);
            ticTacToeButton.setPrefWidth(width);
            strategoButton.setPrefWidth(width);
            if (backButton != null) {
                backButton.setPrefWidth(width);
            }
        }
    }

    /** 
     * Set the height of the buttons
     * @param height the height of the buttons
     */
    public void setButtonHeight(double height) {
        battleshipButton.setPrefHeight(height);
        ticTacToeButton.setPrefHeight(height);
        strategoButton.setPrefHeight(height);
        if (backButton != null) {
            backButton.setPrefHeight(height);
        }
    }

    /** 
     * Set the size of the buttons
     * @param width the width of the buttons
     * @param height the height of the buttons
     */
    public void setButtonSize(double width, double height) {
        setButtonWidth(width);
        setButtonHeight(height);
        if (backButton != null) {
            backButton.setPrefWidth(width);
            backButton.setPrefHeight(height);
        }
    }

    /** 
     * Set the style of the buttons
     * @param style the style string of the buttons
     */
    public void setButtonStyle(String style) {
        battleshipButton.setStyle(style);
        ticTacToeButton.setStyle(style);
        strategoButton.setStyle(style);
        if (backButton != null) {
            backButton.setStyle(style);
        }
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

    /** 
     * Get the back button
     * @return the back button | null if the back button is not enabled
     */
    public CustomButton getBackButton() {
        return backButton;
    }
}
