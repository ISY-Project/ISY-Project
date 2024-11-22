package org.bitshifters.telnet.Events;


public interface Game {
    String MESSAGE = "GAME ";
    void onMatch();
    void onYourTurn(String MESSAGE);
    void onMove(String[] data);
    void onWin();
    void onLose();
    void onDraw();
}
