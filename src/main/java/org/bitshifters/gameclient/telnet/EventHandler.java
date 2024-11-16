package org.bitshifters.gameclient.telnet;

import org.bitshifters.gameclient.telnet.Responses.ChallengeEvent;
import org.bitshifters.gameclient.telnet.Responses.GameEvent;
import org.bitshifters.gameclient.telnet.Responses.ServerEvent;
import org.bitshifters.gameclient.telnet.Commands.Logout;
import org.bitshifters.gameclient.telnet.Events.Error;
import org.bitshifters.gameclient.games.States;

public abstract class EventHandler implements ServerEvent, GameEvent, ChallengeEvent, Error {
    final Logout logout = new Logout();
    private final States gameType;

    public States getGameType() {
        return gameType;
    }

    public boolean isValidGameType(States gameType) {
        if (gameType != this.gameType) {
            return false;
        }
        return true;
    }

    public EventHandler(States gameType) {
        this.gameType = gameType;
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

    public void showMessage(String message) {
        System.out.println(message);
    }

}