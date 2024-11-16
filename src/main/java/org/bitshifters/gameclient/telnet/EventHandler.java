package org.bitshifters.gameclient.telnet;

import org.bitshifters.gameclient.telnet.Events.Challenge;
import org.bitshifters.gameclient.telnet.Events.Error;
import org.bitshifters.gameclient.telnet.Events.Game;
import org.bitshifters.gameclient.telnet.Events.Server;
import org.bitshifters.gameclient.games.GameTypes;

public abstract class EventHandler implements Server, Game, Challenge, Error {
    private final GameTypes gameType;

    public EventHandler(final GameTypes gameType) {
        this.gameType = gameType;
    }

    public GameTypes getGameType() {
        return gameType;
    }

    public boolean isValidGameType(final GameTypes gameType) {
        if (gameType != this.gameType) {
            return false;
        }
        return true;
    }


    public abstract void onChallenge(String playerName, int game, int gameNumber);
    public abstract void onCancel(int gameNumber);
    public abstract void onMatch();
    public abstract void onYourTurn(String message);
    public abstract void onMove(String[] data);
    public abstract void onWin();
    public abstract void onLose();
    public abstract void onDraw();
    public abstract void onHelp(String message);
    public abstract void onError(String message);
    public abstract void onMessage(String message);

    public void showMessage(final String message) {
        System.out.println(message);
    }

}