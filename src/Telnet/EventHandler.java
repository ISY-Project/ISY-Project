package src.Telnet;

import src.GUI.OpponentGrid;
import src.GUI.PlayerGrid;
import src.GUI.TickTackToeGrid;
import src.Telnet.Responses.ChallengeEvent;
import src.Telnet.Responses.GameEvent;
import src.Telnet.Responses.MoveResponse;
import src.Telnet.Responses.ServerEvent;

// TODO Remove the showMessage calls, as they are for debugging.
public class EventHandler implements ServerEvent, GameEvent, ChallengeEvent, Error {
    final Logout logout = new Logout();
    private final TelnetClient client;
    private final OpponentGrid battleshipOpponentGrid;
    private final PlayerGrid battleshipPlayerGrid;
    private final TickTackToeGrid tickTackToeGrid;

    public EventHandler(TelnetClient client, TickTackToeGrid tickTackToeGrid, OpponentGrid battleshipOpponentGrid, PlayerGrid battleshipPlayerGrid) {
        this.client = client;
        this.battleshipOpponentGrid = battleshipOpponentGrid;
        this.battleshipPlayerGrid = battleshipPlayerGrid;
        this.tickTackToeGrid = tickTackToeGrid;
    }

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
        if (result.equals(MoveResponse.TICKTACKTOE)) {
            System.out.println("recived move: " + move);
            this.showMessage(player + " made a move: " + move + " in " + result);
            tickTackToeGrid.updateGrid(Integer.parseInt(move), player);
            return;
        }
        System.out.println("recived move");
        this.showMessage(player + " made a move: " + move + " " + result);
    }

    @Override
    public void onWin() {
        showMessage("Win");
        this.client.sendMessage(logout.get());
    }

    @Override
    public void onLose() {
        showMessage("Lose");
        this.client.sendMessage(logout.get());
    }

    @Override
    public void onDraw() {
        showMessage("Draw");
        this.client.sendMessage(logout.get());
    }

    @Override
    public void onHelp(String message) {
        showMessage(message);
    }

    @Override
    public void onError(String message) {
        showMessage(message);
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onMessage(String message) {
        
    }
}