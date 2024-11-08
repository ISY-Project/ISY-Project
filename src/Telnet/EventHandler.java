package src.Telnet;

import src.GameType;
import src.Telnet.Responses.ChallengeEvent;
import src.Telnet.Responses.GameEvent;
import src.Telnet.Responses.ServerEvent;


public abstract class EventHandler implements ServerEvent, GameEvent, ChallengeEvent, Error {
    final Logout logout = new Logout();
    private final GameType gameType;

    public GameType getGameType() {
        return gameType;
    }

    public boolean isValidGameType(GameType gameType) {
        if (gameType != this.gameType) {
            return false;
        }
        return true;
    }

    public EventHandler(GameType gameType) {
        System.out.println(gameType);
        this.gameType = gameType;
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

    public void showMessage(String message) {
        System.out.println(message);
    }

}