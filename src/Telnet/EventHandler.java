package Telnet;

import Telnet.Responses.ChallengeEvent;
import Telnet.Responses.GameEvent;
import Telnet.Responses.MoveResponse;
import Telnet.Responses.ServerEvent;

public class EventHandler implements ServerEvent, GameEvent, ChallengeEvent {
    private TelnetClient client;

    EventHandler(TelnetClient client) {
        this.client = client;
    }

    @Override
    public void onChallenge(String playerName, int game, int gameNumber) {
        // TODO Auto-generated method stub. Automatically accept the challenge.
        throw new UnsupportedOperationException("Unimplemented method 'onChallenge'");
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