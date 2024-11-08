package src.Telnet;

import src.GameType;
import src.Main;
import src.Telnet.Responses.ChallengeEvent;
import src.Telnet.Responses.GameEvent;
import src.Telnet.Responses.ServerEvent;


public abstract class EventHandler implements ServerEvent, GameEvent, ChallengeEvent, Error {
    final Logout logout = new Logout();
    private GameType gameType;

    // TODO: get a gametype from the SVR GAME MATCH response.
    public boolean isValidGameType() {
        // System.out.println(Main.getGameType() + " " + this.gameType);
        if (Main.getGameType() == GameType.NONE || Main.getGameType() == this.gameType) {
            // System.out.println("Valid game type");
            return true;
        }
        // System.out.println("Invalid game type");
        return false;
    }

    public EventHandler(GameType gameType) {
        System.out.println(gameType);
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