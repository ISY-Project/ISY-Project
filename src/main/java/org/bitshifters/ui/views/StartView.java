package org.bitshifters.ui.views;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.NavigationButtons;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class StartView extends BorderPane {
    public StartView(MainFrame mainFrame) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        NavigationButtons hButtonBox = new NavigationButtons(mainFrame, false);

        hButtonBox.setButtonStyle("-fx-background-color: #aaddaa; -fx-font-size: 4em; -fx-text-fill: #000000;");
        hButtonBox.setButtonSize(400, 100);

        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.add(hButtonBox, 0, 0);
        this.setCenter(grid);
    }
}
