package org.bitshifters.telnet;

public interface OnConnectionSwitch {
    void handle(boolean isConnected);
}
