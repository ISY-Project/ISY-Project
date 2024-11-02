package src;

import src.GUI.BattleshipGUI;
import src.GameEngine.BattleshipEngine;
import src.GameEngine.Ship;
import src.Telnet.EventHandler;
import src.Telnet.Logout;
import src.Telnet.Place;
import src.Telnet.TelnetClient;
import src.Telnet.Responses.MoveResponse;

public class BattleshipHandler extends EventHandler {
    final Logout logout = new Logout();
    private TelnetClient client;
    private BattleshipGUI gui;
    private BattleshipEngine engine;

    public BattleshipHandler(TelnetClient client, BattleshipGUI gui, BattleshipEngine engine) {
        super(client);
        this.gui = gui;
        this.engine = engine;
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
        int size = 6 ;
        boolean isHorizontal = true;
        int[] position = engine.getBestShipSpot(size, isHorizontal);
        int x = position[0];
        int y = position[1];
        String direction = isHorizontal ? "East" : "South";
        Ship ship = new Ship(size, isHorizontal, x, y);
        engine.placeShip(ship);
        this.client.sendMessage(new Place(x, y, direction).get());
    }

    @Override
    public void onYourTurn(String message) {
        this.showMessage("Your turn: " + message);
        int[] bestMove = engine.getBestMove();
        engine.shoot(bestMove);
        this.showMessage("Shooting at " + bestMove);
    }

    @Override
    public void onMove(String player, String move, MoveResponse result) {
        System.out.println("received move");
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

    public void showMessage(String message) {
        System.out.println("Received: " + message);
        this.gui.getChatBox().addMessage("You", message);
    }

    @Override
    public void onMessage(String message) {
        this.gui.getChatBox().addMessage(message);
    }

}