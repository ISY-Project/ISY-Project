package org.bitshifters.gameclient.telnet.Responses;


public interface GameEvent {
    String MESSAGE = "GAME ";
    void onMatch();
    void onYourTurn(String MESSAGE);
    void onMove(String[] data);
    void onWin();
    void onLose();
    void onDraw();
}
