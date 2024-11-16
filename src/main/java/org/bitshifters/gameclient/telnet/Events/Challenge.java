package org.bitshifters.gameclient.telnet.Events;

public interface Challenge {
    String MESSAGE = "CHALLENGE ";
    void onChallenge(String playerName, int game, int gameNumber);
    void onCancel(int gameNumber);
}
