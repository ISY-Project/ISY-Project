package org.bitshifters.telnet.Notifiers.interfaces;

public interface OnYourTurnSwitch {
    /**
     * Called when the players turn is switched
     * @param isConnected
     */
    void OnYourTurnSwitch(boolean yourTurn);
    void OnYourTurnSwitch(String yourTurnText);
}
