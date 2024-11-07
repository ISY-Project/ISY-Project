package src.Telnet;

import src.GameType;
import src.Telnet.Responses.ChallengeEvent;
import src.Telnet.Responses.GameEvent;
import src.Telnet.Responses.ServerEvent;


public abstract class EventHandler implements ServerEvent, GameEvent, ChallengeEvent, Error {
    final Logout logout = new Logout();
    private final GameType gameType;

    public boolean isValidGameType(GameType gameType) {
        if (gameType != this.gameType) {
            return false;
        }
        return true;
    }

    public EventHandler(GameType gameType) {
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