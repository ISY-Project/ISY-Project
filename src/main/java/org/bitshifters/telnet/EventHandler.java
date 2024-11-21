package org.bitshifters.telnet;

import org.bitshifters.gameclient.games.GameTypes;
import org.bitshifters.telnet.Events.Challenge;
import org.bitshifters.telnet.Events.Error;
import org.bitshifters.telnet.Events.Game;
import org.bitshifters.telnet.Events.Server;

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


    @Override
    public abstract void onChallenge(String playerName, int game, int gameNumber);

    @Override
    public abstract void onCancel(int gameNumber);

    @Override
    public abstract void onMatch();

    @Override
    public abstract void onYourTurn(String message);

    @Override
    public abstract void onMove(String[] data);

    @Override
    public abstract void onWin();

    @Override
    public abstract void onLose();

    @Override
    public abstract void onDraw();

    @Override
    public abstract void onHelp(String message);
    
    @Override
    public abstract void onError(String message);

    @Override
    public abstract void onMessage(String message);

    public void showMessage(final String message) {
        System.out.println(message);
    }

}