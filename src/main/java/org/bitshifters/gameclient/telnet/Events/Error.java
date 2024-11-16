package org.bitshifters.gameclient.telnet.Events;

public interface Error {
    String MESSAGE = "ERR ";
    String NOT_LOGGED_IN = MESSAGE + "Not logged in";

    void onError(String error);
}
