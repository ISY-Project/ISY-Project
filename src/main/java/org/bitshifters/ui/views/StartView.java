package org.bitshifters.ui.views;

import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.NavigationButtons;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StartView extends BorderPane {
    public StartView(MainFrame mainFrame) {
        HBox hButtonBox = new NavigationButtons(mainFrame);

        this.setCenter(hButtonBox);
    }
}
