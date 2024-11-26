package org.bitshifters.ui.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.bitshifters.enums.TicTacToeCell;
import org.bitshifters.logging.BsLogger;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.BaseGrid;
import org.bitshifters.ui.componenets.NavigationButtons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicTacToeView extends BorderPane {
    private static final BsLogger logger = new BsLogger(TicTacToeView.class);
    private final BaseGrid baseGrid;

    public BaseGrid getBaseGrid() {
        return this.baseGrid;
    }

    public TicTacToeView(MainFrame mainFrame) {
        HBox hGridBox = new HBox();
        VBox vBox = new VBox();
        VBox hButtonBox = new NavigationButtons(mainFrame, true, false);

        this.baseGrid = new BaseGrid(3);

        fillGrid();

        hGridBox.getChildren().addAll(this.baseGrid);
        hGridBox.setAlignment(javafx.geometry.Pos.CENTER);  

        vBox.getChildren().addAll(hGridBox);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        this.setCenter(vBox);
        this.setRight(hButtonBox);
    }

    public final void fillGrid() {
        logger.debug("Filling grid");
        int buttonSize = 200;
        Button[][] buttonGrid = this.baseGrid.getButtonGrid();
        for (int row = 0; row < this.baseGrid.getGridHeight(); row++) {
            for (int col = 0; col < this.baseGrid.getGridHeight(); col++) {
                Button button = buttonGrid[row][col];
                button.setMinSize(buttonSize, buttonSize);
                button.setMaxSize(buttonSize, buttonSize);

                button.setStyle("-fx-font-size: 8em; "); // IMPORTAINT every time setStyle is called, it overrides the previous style
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
