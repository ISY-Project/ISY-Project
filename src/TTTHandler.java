package src;

import javax.swing.JOptionPane;
import src.GUI.MainFrame;
import src.GUI.Screens;
import src.GUI.TickTackToe;
import src.Telnet.EventHandler;
import src.Telnet.Responses.MoveResponse;
import src.Telnet.Subscribe;
import src.Telnet.TelnetClient;

// TODO Remove the showMessage calls, as they are for debugging. Instead show the messages in the GUI
public class TTTHandler extends EventHandler {
    // final Logout logout = new Logout();
    @SuppressWarnings("unused")
    private TelnetClient client;
    private final TickTackToe tickTackToe;
    private final MainFrame mainFrame;

    // , MainFrame mainFrame, TickTackToeGrid tickTackToeGrid, OpponentGrid battleshipOpponentGrid, PlayerGrid battleshipPlayerGrid
    public TTTHandler(TelnetClient client, MainFrame mainFrame, TickTackToe tickTackToe) {
        super(client);
        this.tickTackToe = tickTackToe;
        this.mainFrame = mainFrame;
    }

    @Override
    public void onChallenge(String playerName, int game, int gameNumber) {
        this.showServerMessage(playerName + " has challenged you to a game of " + game + " with game number " + gameNumber);
    }

    @Override
    public void onCancel(int gameNumber) {
        this.showServerMessage("Game " + gameNumber + " has been canceled");
    }

    @Override
    public void onMatch() {
        this.showServerMessage("Match started (old method)");
    }

    @Override
    public void onMatch(Subscribe game) {
        Main.setInMatch(true);
        if (game.equals(Subscribe.TICTACTOE)) {
            Main.toggleTTTGrid();
            Main.getMainFrame().showScreen(Screens.TICK_TACK_TOE);
            tickTackToe.getTickTackToeGrid().clearGrid();
        }
        this.showServerMessage("The match started");
    }

    @Override
    public void onYourTurn(String message) {
        mainFrame.setIsPlayerTurn(true);
        if (tickTackToe.getTickTackToeGrid().isFirstMove()) {
            tickTackToe.getTickTackToeGrid().setIsPlayerX(true);
            tickTackToe.getInformationPanel().setYourTurnLable("It's Your turn");
        }
        if (mainFrame.getAlgorithmOn()) {
            tickTackToe.getTickTackToeGrid().algMakeMove();
        }
        // this.showServerMessage("Your turn: " + message);
        // tickTackToe.setItsYourTurn(true);
    }

    @Override
    public void onMove(String player, String move, MoveResponse result) {
        tickTackToe.getTickTackToeGrid().setFirstMove(false);
        if (result.equals(MoveResponse.TICKTACKTOE)) {
            System.out.println("recived move: " + move);
            // this.showServerMessage(player + " made a move: " + move + " in " + result);
            tickTackToe.getTickTackToeGrid().updateGrid(Integer.parseInt(move), player);
            return;
        }
        System.out.println("recived move");
        // this.showServerMessage(player + " made a move: " + move + " " + result);
    }

    @Override
    public void onWin() {
        showServerMessage("You have Won");
        JOptionPane.showMessageDialog(this.mainFrame, "You won the game");
        Main.setInMatch(false);
        tickTackToe.getTickTackToeGrid().disableGrid();
    }

    @Override
    public void onLose() {
        showServerMessage("You have lost");
        JOptionPane.showMessageDialog(this.mainFrame, "You lost the game");
        Main.setInMatch(false);
        tickTackToe.getTickTackToeGrid().disableGrid();
    }

    @Override
    public void onDraw() {
        showServerMessage("Its a draw");
        JOptionPane.showMessageDialog(this.mainFrame, "The game ended in a draw");
        Main.setInMatch(false);
        tickTackToe.getTickTackToeGrid().disableGrid();
    }

    @Override
    public void onHelp(String message) {
        showServerMessage(message);
    }

    @Override
    public void onError(String message) {
        showServerMessage(message);
    }

    public void showMessage(String message) {
        System.out.println("Received: " + message);
        this.tickTackToe.getChatBox().addMessage("You", message);
    }

    public void showServerMessage(String message) {
        System.out.println("Received: " + message);
        this.tickTackToe.getChatBox().addMessage("Server", message);
    }

    @Override
    public void onMessage(String message) {
        this.tickTackToe.getChatBox().addMessage(message);
    }

}