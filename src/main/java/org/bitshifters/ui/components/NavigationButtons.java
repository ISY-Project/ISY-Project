package org.bitshifters.ui.components;

import java.util.Optional;

import org.bitshifters.ClientController;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.enums.Screens;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationButtons extends VBox {
    private final CustomButton battleshipButton;
    private final CustomButton ticTacToeButton;
    private final CustomButton strategoTenButton;
    private final CustomButton strategoEightButton;
    private final CustomButton backButton;
    private final CustomButton resetButton;
    private final Boolean isHorizontal;

    /** 
     * Constructor for the NavigationButtons
     * @param mainFrame the main frame object
     * @param enableBackButton enable the back button
     * @param isHorizontal is the navigation buttons horizontal or not?
     */
    public NavigationButtons(MainFrame mainFrame, Boolean enableBackButton, Boolean isHorizontal) {
        super(5);
        setPrefWidth(110);
        this.isHorizontal = isHorizontal;

        String style = ""; // defualt button style

        this.battleshipButton = new CustomButton("Battleship", getPrefWidth(), style);
        this.ticTacToeButton = new CustomButton("TicTacToe", getPrefWidth(), style);
        this.strategoTenButton = new CustomButton("Stratego (10x10)", getPrefWidth(), style);
        this.strategoEightButton = new CustomButton("Stratego (8x8)", getPrefWidth(), style);

        battleshipButton.setOnAction(_ -> mainFrame.showScreen(Screens.BATTLESHIP));
        ticTacToeButton.setOnAction(_ -> mainFrame.showScreen(Screens.TICTACTOE));
        strategoTenButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGOTEN));
        strategoEightButton.setOnAction(_ -> mainFrame.showScreen(Screens.STRATEGOEIGHT));

        var cc = ClientController.getInstance();
        ticTacToeButton.addOnAction(_ -> ClientController.setSelectedClient(Optional.of(cc.getTicTacToeClient())));
        strategoTenButton.addOnAction(_ -> ClientController.setSelectedClient(Optional.of(cc.getStrategoClientTen())));
        strategoEightButton.addOnAction(_ -> ClientController.setSelectedClient(Optional.of(cc.getStrategoClientEight())));

        if (enableBackButton) {
            this.backButton = new CustomButton("Back", getPrefWidth(), style);
            backButton.setOnAction(_ -> backButtonAction(mainFrame));
            backButton.addOnAction(_ -> ClientController.setSelectedClient(Optional.empty()));
        } else {
            this.backButton = null;
        }

        if (isHorizontal) {
            HBox hBox = new HBox();
            hBox.setSpacing(5);
            hBox.getChildren().addAll(battleshipButton, ticTacToeButton, strategoTenButton, strategoEightButton);
            if (backButton != null) {
                hBox.getChildren().add(backButton);
            }
            getChildren().add(hBox);
            this.resetButton = null;
        } else {
            this.resetButton = new CustomButton("Reset Game", getPrefWidth(), style);
            if (backButton != null) {
                getChildren().addAll(battleshipButton, ticTacToeButton, strategoTenButton, strategoEightButton, backButton, resetButton);
            } else {
                getChildren().addAll(battleshipButton, ticTacToeButton, strategoTenButton, strategoEightButton, resetButton);
            }
        }
    }

    private void backButtonAction(MainFrame mainFrame) {
        mainFrame.showScreen(Screens.START_SCREEN);
        // TODO: reset games.
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
            strategoTenButton.setPrefWidth(width);
            strategoEightButton.setPrefWidth(width);
            if (backButton != null) {
                setPrefWidth(width*4);
                backButton.setPrefWidth(width);
            }
        } else {
            setPrefWidth(width);
            battleshipButton.setPrefWidth(width);
            ticTacToeButton.setPrefWidth(width);
            strategoTenButton.setPrefWidth(width);
            strategoEightButton.setPrefWidth(width);
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
        strategoTenButton.setPrefHeight(height);
        strategoEightButton.setPrefHeight(height);
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
        strategoTenButton.setStyle(style);
        strategoEightButton.setStyle(style);
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
    public CustomButton getStrategoTenButton() {
        return strategoTenButton;
    }

    /** 
     * Get the stratego button
     * @return the stratego button
     */
    public CustomButton getStrategoEightButton() {
        return strategoEightButton;
    }

    /** 
     * Get the back button
     * @return the back button | null if the back button is not enabled
     */
    public CustomButton getBackButton() {
        return backButton;
    }

    public CustomButton getResetButton() {
        return resetButton;
    }
}
