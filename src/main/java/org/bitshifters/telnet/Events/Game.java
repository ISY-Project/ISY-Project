package org.bitshifters.telnet.Events;

import org.bitshifters.telnet.MatchData;

public interface Game {
    String MESSAGE = "GAME ";
    void onMatch(MatchData data);
    void onYourTurn(String MESSAGE);
    void onMove(String[] data);
    void onWin();
    void onLose();
    void onDraw();
}
