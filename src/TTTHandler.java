package src;

import src.GUI.TickTackToe;
import src.Telnet.EventHandler;
import src.Telnet.Logout;
import src.Telnet.TelnetClient;
import src.Telnet.Responses.MoveResponse;


public class TTTHandler extends EventHandler {
    final Logout logout = new Logout();
    private TelnetClient client;
    private TickTackToe gui; // Expect any gui. Not just final BattleshipGUI, but also TickTackToeGUI

    public TTTHandler(TelnetClient client, TickTackToe gui) {
        super(client);
        this.gui = gui;
    }

    @Override
    public void onMove(String player, String move, MoveResponse result) {
        System.out.println("received move: " + move);
        this.showMessage(player + " made a move: " + move + " in " + result);
        this.gui.getTickTackToeGrid().updateGrid(Integer.parseInt(move), player);
        return;
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