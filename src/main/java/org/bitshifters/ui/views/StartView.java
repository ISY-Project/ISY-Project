package org.bitshifters.ui.views;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.NavigationButtons;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StartView extends BorderPane {
    public StartView(MainFrame mainFrame) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        VBox hButtonBox = new NavigationButtons(mainFrame);
        grid.add(hButtonBox, 0, 0);
        this.setCenter(grid);
    }
}
