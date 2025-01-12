package org.bitshifters.ui.components;

import org.bitshifters.telnet.Notifiers.Listener;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Status extends HBox implements Listener<Boolean> {
    private final Label statusLabel;

    /**
     * Constructor for the Status
     */
    public Status() {
        super();
        this.statusLabel = new Label();
        this.getChildren().add(statusLabel);
    }

    /**
     * Set the status text
     * @param text the text to set
     */
    public void setLabel(String text) {
        this.statusLabel.setText(text);
    }

    /**
     * Set the status text based on a boolean
     * @param bool the boolean to set the text based on
     */
    public void setLabel(boolean bool) {
        String text = "Connection Status " + (bool ? "Connected" : "Disconnected");
        this.statusLabel.setText(text);
    }

    /**
     * Get the status text
     * @return the status text
     */
    public String getStatusText() {
        return this.statusLabel.getText();
    }

    @Override
    public void callback(Boolean value) {
        Platform.runLater(() -> setLabel(value));
    }
}
