package org.bitshifters.ui.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.bitshifters.games.tictactoe.TicTacToeCell;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Notifiers.ConnectionNotifier;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.components.BaseGrid;
import org.bitshifters.ui.components.CustomBorderPane;
import org.bitshifters.ui.components.NavigationButtons;
import org.bitshifters.ui.components.Status;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicTacToeView extends CustomBorderPane {
    private static final BSLogger logger = new BSLogger(TicTacToeView.class);
    private final NavigationButtons hButtonBox;
    private final BaseGrid baseGrid;

    /** 
     * Constructor for the TicTacToeView
     * @param mainFrame the main frame object
     */
    public TicTacToeView(MainFrame mainFrame) {
        super(mainFrame);
        logger.debug("Creating TicTacToeView");
        HBox hGridBox = new HBox();
        VBox vBox = new VBox();
        hButtonBox = new NavigationButtons(mainFrame, true, false);

        this.baseGrid = new BaseGrid(3);

        Status status = new Status();
        ConnectionNotifier.register(status);

        fillGrid();

        hGridBox.getChildren().addAll(this.baseGrid);
        hGridBox.setAlignment(javafx.geometry.Pos.CENTER);  

        status.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.getChildren().addAll(status);
        
        vBox.getChildren().addAll(hGridBox);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        this.setCenter(vBox);
        this.setRight(hButtonBox);
    }

    /** 
     * Get the base grid
     * @return the base grid object with the buttons
     */
    public BaseGrid getBaseGrid() {
        return this.baseGrid;
    }

    /** 
     * Get the button at the specified row and column
     * @param row the row number of the button
     * @param col the column number of the button
     * @return the button at the specified row and column
     */
    public Button getButton(int row, int col) {
        return this.baseGrid.getButtonGrid()[row][col];
    }

    /** 
     * Update the button at the specified row and column with the specified cell
     * @param row the row number of the button
     * @param col the column number of the button
     * @param cell the cell to update the button with
     */
    public final void updateButton(int row, int col, TicTacToeCell cell) {
        this.updateButton(getButton(row, col), cell);
    }

    /** 
     * Update the specified button with the specified cell
     * @param button the button to update
     * @param cell the cell to update the button with
     */
    public final void updateButton(Button button, TicTacToeCell cell) {
        try{ 
            if (cell.equals(TicTacToeCell.X)) {
                FileInputStream input = new FileInputStream(cell.getRedPath());
                Image image = new Image(input);
                button.setGraphic(new ImageView(image));
            } else if (cell.equals(TicTacToeCell.O)) {
                FileInputStream input = new FileInputStream(cell.getBluePath());
                Image image = new Image(input);
                button.setGraphic(new ImageView(image));
            } else {
                button.setText("");
                button.setGraphic(null);
            }
        } catch (FileNotFoundException e) {
            logger.error("Error loading image", e);
        }
        button.setUserData(cell);
        // button.setText(cell.getChar());
        button.setStyle("-fx-text-fill: #ff0000; -fx-font-size: 8em;");
    }


    /** 
     * Fill the grid with buttons
     */
    public final void fillGrid() {
        logger.debug("Filling grid");
        int buttonSize = 200;
        Button[][] buttonGrid = this.baseGrid.getButtonGrid();
        for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
            for (int col = 0; col < this.baseGrid.getGridHeight(); col++) {
                Button button = buttonGrid[row][col];
                button.setMinSize(buttonSize, buttonSize);
                button.setMaxSize(buttonSize, buttonSize);

                button.setStyle("-fx-font-size: 8em; "); // IMPORTANT every time setStyle is called, it overrides the previous style
                button.setOnAction(_ -> {
                    if (button.getUserData() == null || button.getUserData().equals(TicTacToeCell.O)) {
                        try {
                            for (int i = 0; i < this.baseGrid.getGridHeight(); i++) {
                                for (int j = 0; j < this.baseGrid.getGridHeight(); j++) {
                                    Button b = buttonGrid[i][j];
                                    if (b.equals(button)) {
                                        var list = new ArrayList<Object>(3);
                                        list.add(i);
                                        list.add(j);
                                        list.add(TicTacToeCell.X);
                                        b.setUserData(list);
                                    }
                                }
                            }
                            FileInputStream input = new FileInputStream(TicTacToeCell.X.getRedPath());
                            Image image = new Image(input);

                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(buttonSize);
                            imageView.setFitWidth(buttonSize);
                            button.setGraphic(imageView);
                            logger.debug("Button clicked: " + button.getUserData());
                        } catch (FileNotFoundException e) {
                            logger.error("Error loading image", e);
                            button.setText("X");
                            button.setStyle("-fx-text-fill: #ff0000; -fx-font-size: 8em;");
                        }
                    } else {
                        try {
                            FileInputStream input = new FileInputStream(TicTacToeCell.O.getBluePath());
                            Image image = new Image(input);

                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(buttonSize);
                            imageView.setFitWidth(buttonSize);
                            button.setGraphic(imageView);
                            button.setUserData(TicTacToeCell.O);
                        } catch (FileNotFoundException e) {
                            logger.error("Error loading image", e);
                            button.setText("O");
                            button.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 8em;");
                        }
                    }
                });
            }
        }
    }
}
