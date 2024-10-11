package Telnet;

import Telnet.Responses.ChallengeEvent;
import Telnet.Responses.GameEvent;
import Telnet.Responses.MoveResponse;
import Telnet.Responses.ServerEvent;


// TODO Remove the showMessage calls, as they are for debugging.
public class EventHandler implements ServerEvent, GameEvent, ChallengeEvent {
    @Override
    public void onChallenge(String playerName, int game, int gameNumber) {
        this.showMessage(playerName + " has challenged you to a game of " + game + " with game number " + gameNumber);
    }

    @Override
    public void onCancel(int gameNumber) {
        this.showMessage("Game " + gameNumber + " has been canceled");
    }

    @Override
    public void onMatch() {
        this.showMessage("Match started");
    }

    @Override
    public void onYourTurn(String message) {
        this.showMessage("Your turn: " + message);
    }

    @Override
    public void onMove(String player, String move, MoveResponse result) {
        this.showMessage(player + " made a move: " + move + " " + result);
    }

    @Override
    public void onWin() {
        showMessage("Win");
    }

    @Override
    public void onLose() {
        showMessage("Lose");
    }

    @Override
    public void onDraw() {
        showMessage("Draw");
    }

    @Override
    public void onHelp(String message) {
        showMessage(message);
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

}