package org.bitshifters.telnet;

public interface OnConnectionSwitch {
    /**
     * Called when the connection is switched
     * @param isConnected
     */
    void OnConnectionSwitch(boolean isConnected);
}
