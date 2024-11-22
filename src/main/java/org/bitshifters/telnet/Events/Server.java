package org.bitshifters.telnet.Events;

public interface Server {
    String MESSAGE = "SVR ";
    void onHelp(String MESSAGE);
    void onMessage(String MESSAGE);
}
