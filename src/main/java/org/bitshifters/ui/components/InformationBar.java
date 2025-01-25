package org.bitshifters.ui.components;

import org.bitshifters.telnet.MatchData;
import org.bitshifters.telnet.Notifiers.ConnectionNotifier;
import org.bitshifters.telnet.Notifiers.YourTurnNotifier;
import org.bitshifters.telnet.Notifiers.interfaces.OnConnectionSwitch;
import org.bitshifters.telnet.Notifiers.interfaces.OnYourTurnSwitch;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InformationBar extends HBox implements OnConnectionSwitch, OnYourTurnSwitch{
    private final Label statusLabel;
    private final Label yourTurnLabel;
    private MatchData matchData;

    /**
     * Constructor for the Status
     */
    public InformationBar() {
        super(20);
        this.statusLabel = new Label();
        this.yourTurnLabel = new Label("Waiting On Opponent");
        this.getChildren().addAll(statusLabel, yourTurnLabel);
        registerListeners();
    }

    /**
     * Register the listeners
     */
    private void registerListeners() {
        ConnectionNotifier.register(this);
        YourTurnNotifier.register(this);
    }

    /**
     * Set the status text
     * @param text the text to set
     */
    public void setConnectionLabel(String text) {
        this.statusLabel.setText(text);
    }

    /**
     * Set the status text based on a boolean
     * @param bool the boolean to set the text based on
     */
    public void setConnectionLabel(boolean bool) {
        String text = "Connection Status " + (bool ? "Connected" : "Disconnected");
        this.statusLabel.setText(text);
    }

    /**
     * Get the status text
     * @return the status text
     */
    public String getConnectionLabelText() {
        return this.statusLabel.getText();
    }

    /**
     * Called when the connection is switched
     * @param isConnected the connection status
     */
    @Override
    public void OnConnectionSwitch(boolean isConnected) {
        Platform.runLater(() -> setConnectionLabel(isConnected));
    }

    /**
     * Called when the connection is switched
     * @param connectionText the connection status
     */
    @Override
    public void OnConnectionSwitch(String connectionText) {
        Platform.runLater(() -> setConnectionLabel(connectionText));
    }

    /**
     * Set the your turn text
     * @param text the text to set
     */
    public void setYourTurnLabel(String text) {
        this.yourTurnLabel.setText(text);
    }

    /**
     * Set the your turn text based on a boolean
     * @param yourTurn the boolean to set the text based on
     */
    public void setYourTurnLabel(boolean yourTurn) {
        String opponentName = (matchData == null) ? "Opponent" : matchData.opponentName;
        String text = yourTurn ? "It's Your Turn" : "It's " + opponentName +"'s Turn";
        this.yourTurnLabel.setText(text);
    }

    /**
     * Get the your turn text
     * @return the your turn text
     */
    public String getYourTurnLabelText() {
        return this.yourTurnLabel.getText();
    }

    /**
     * Called when the players turn is switched
     * @param yourTurn the turn status
     */
    @Override
    public void OnYourTurnSwitch(boolean yourTurn) {
        Platform.runLater(() -> setYourTurnLabel(yourTurn));
    }

    /**
     * Called when the players turn is switched
     * @param yourTurnText the turn status
     */
    @Override
    public void OnYourTurnSwitch(String yourTurnText) {
        Platform.runLater(() -> setYourTurnLabel(yourTurnText));
    }

    public void setMatchData(MatchData data) {
        this.matchData = data;
        Platform.runLater(() -> setYourTurnLabel(false));
    }
}
