package src;

import javax.swing.JOptionPane;
import src.GUI.BattleshipGUI;
import src.GUI.MainFrame;
import src.GUI.TickTackToeGrid;
import src.Telnet.EventHandler;
import src.Telnet.Responses.MoveResponse;
import src.Telnet.TelnetClient;

// TODO Remove the showMessage calls, as they are for debugging. Instead show the messages in the GUI
public class Handler extends EventHandler {
    // final Logout logout = new Logout();
    private TelnetClient client;
    private final BattleshipGUI battleshipGUI;
    private final TickTackToeGrid tickTackToeGrid;
    private final MainFrame mainFrame;

    // , MainFrame mainFrame, TickTackToeGrid tickTackToeGrid, OpponentGrid battleshipOpponentGrid, PlayerGrid battleshipPlayerGrid
    public Handler(TelnetClient client, MainFrame mainFrame, BattleshipGUI battleshipGUI, TickTackToeGrid tickTackToeGrid) {
        super(client);
        this.battleshipGUI = battleshipGUI;
        this.tickTackToeGrid = tickTackToeGrid;
        this.mainFrame = mainFrame;
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
        mainFrame.setIsPlayerTurn(true);
        if (mainFrame.getAlgorithmOn()) {
            tickTackToeGrid.algMakeMove();
        }
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
        JOptionPane.showMessageDialog(this.mainFrame, "You won the game");
        // this.client.sendMessage(logout.get());
    }

    @Override
    public void onLose() {
        showMessage("Lose");
        JOptionPane.showMessageDialog(this.mainFrame, "You lost the game");
        // this.client.sendMessage(logout.get());
    }

    @Override
    public void onDraw() {
        showMessage("Draw");
        JOptionPane.showMessageDialog(this.mainFrame, "The game ended in a draw");
        // this.client.sendMessage(logout.get());
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
        this.battleshipGUI.getChatBox().addMessage("You", message);
    }

    @Override
    public void onMessage(String message) {
        this.battleshipGUI.getChatBox().addMessage(message);
    }

}