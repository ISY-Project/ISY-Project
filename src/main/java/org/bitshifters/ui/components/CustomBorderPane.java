package org.bitshifters.ui.components;

import org.bitshifters.ui.MainFrame;

import javafx.scene.layout.BorderPane;

public class CustomBorderPane extends BorderPane {
    private final MainFrame mainFrame;

    public CustomBorderPane(MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
    }
    
    /**
     * Get the main frame
     * @return the main frame object
     */
    public MainFrame getMainFrame() {
        return this.mainFrame;
    }
}
