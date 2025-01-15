package org.bitshifters.ui.components;

import org.bitshifters.telnet.Notifiers.Listener;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TurnIndicator extends HBox implements Listener<Boolean> {
    private final Label label;

    /**
     * Constructor for the TurnInfo
     */
    public TurnIndicator() {
        super();
        this.label = new Label();
        this.getChildren().add(label);
    }

    /**
     * Set the TurnInfo text
     * @param text the text to set
     */
    public void setLabel(String text) {
        this.label.setText(text);
    }

    /**
     * Set the TurnInfo text based on a boolean
     * @param bool the boolean to set the text based on
     */
    public void setLabel(boolean bool) {
        String text = "TurnInfo " + (bool ? "Your Turn" : "opponent's turn");
        this.label.setText(text);
    }

    /**
     * Get the TurnInfo text
     * @returnInfo the TurnInfo text
     */
    public String getTurnInfoText() {
        return this.label.getText();
    }

    @Override
    public void callback(Boolean value) {
        Platform.runLater(() -> setLabel(value));
    }
}
