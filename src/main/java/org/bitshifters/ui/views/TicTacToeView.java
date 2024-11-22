package org.bitshifters.ui.views;

import org.bitshifters.logging.BsLogger;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.BaseGrid;
import org.bitshifters.ui.componenets.NavigationButtons;

import javafx.scene.control.Button;
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
        HBox hButtonBox = new NavigationButtons(mainFrame);

        this.baseGrid = new BaseGrid(3);

        fillGrid();

        hGridBox.getChildren().addAll(this.baseGrid);
        vBox.getChildren().addAll(hGridBox, hButtonBox);

        this.setCenter(vBox);
    }

    public final void fillGrid() {
        logger.debug("Filling grid");
        int buttonSize = 100;
        Button[][] buttonGrid = this.baseGrid.getButtonGrid();
        for (Button[] row : buttonGrid) {
            for (Button cell : row) {
                cell.setMinSize(buttonSize, buttonSize);
                cell.setMaxSize(buttonSize, buttonSize);
                cell.setStyle("-fx-font-size: 3.5em; "); // IMPORTAINT every time setStyle is called, it overrides the previous style
                cell.setOnAction(_ -> {
                    if (cell.getText().isEmpty()) {
                        cell.setText("X");
                        cell.setStyle("-fx-text-fill: #ff0000; -fx-font-size: 3.5em;");
                    } else {
                        cell.setText("O");
                        cell.setStyle("-fx-text-fill: #0000ff; -fx-font-size: 3.5em;");
                    }
                });
            }
        }
    }
}
