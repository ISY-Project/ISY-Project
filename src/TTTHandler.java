package src;

import src.GUI.TickTackToeGui;
import src.GameEngine.TTTEngine;
import src.Telnet.EventHandler;
import src.Telnet.Logout;
import src.Telnet.Move;
import src.Telnet.TelnetClient;
import src.Telnet.Responses.MoveResponse;


public class TTTHandler extends EventHandler {
    final Logout logout = new Logout();
    private TickTackToeGui gui;
    private TTTEngine engine;
    private String playerName;

    public TTTHandler(TelnetClient client, TickTackToeGui gui, String playerName, TTTEngine engine) {
        super(client);
        this.gui = gui;
        this.playerName = playerName;
        this.engine = engine;
    }

    @Override
    public void onMove(String player, String move, MoveResponse result) {
        char playerChar = player.equals(this.playerName) ? 'X' : 'O';
        this.showMessage(playerChar + " placed at " + move);
        this.gui.getPlayerGrid().updateCell(Integer.parseInt(move), playerChar);
        this.engine.makeMove(Integer.parseInt(move));
        return;
    }

    @Override
    public void onYourTurn(String message) {
        this.showMessage("Your turn: " + message);
        engine.setAlgorithmOn(true);
        if (engine.getAlgorithmOn()) {
            int bestMove = engine.getBestMove();
            engine.makeMove(bestMove);
            this.client.sendMessage(new Move(bestMove).get());
            this.showMessage("Placing at " + bestMove);
        }
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

}