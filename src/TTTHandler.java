package src;

import java.util.logging.Logger;

import javax.swing.JOptionPane;
import src.GUI.MainFrame;
import src.GUI.Screens;
import src.GUI.TickTackToeGrid;
import src.Logging.LoggerFactory;
import src.Telnet.EventHandler;
import src.Telnet.Responses.MoveResponse;

public class TTTHandler extends EventHandler {
    private final Logger logger;
    private final TickTackToeGrid tickTackToeGrid;
    private final MainFrame mainFrame;

    public TTTHandler(TickTackToeGrid tickTackToeGrid) {
        super(GameType.TTT);
        this.tickTackToeGrid = tickTackToeGrid;
        this.mainFrame = Main.getMainFrame();
        this.logger = LoggerFactory.getChildLogger(Main.getLogger(), this.getClass());
    }

    public void onChallenge(String playerName, int game, int gameNumber) {
        this.showMessage(playerName + " has challenged you to a game of " + game + " with game number " + gameNumber);
    }

    public void onCancel(int gameNumber) {
        this.showMessage("Game " + gameNumber + " has been canceled");
    }

    public void onMatch() {
        if (Main.getGameType() == GameType.NONE) {
            // If we didn't start the game ourselves, we move to it and turn on comp.
            Main.getMainFrame().showScreen(Screens.TTT);
            mainFrame.setAlgorithmOn(true);
        }
        Main.setGameType(GameType.TTT);
        Main.toggleTTTGrid();
        tickTackToeGrid.clearGrid();
        this.showMessage("Match started");
    }

    public void onYourTurn(String message) {
        mainFrame.setIsPlayerTurn(true);
        if (mainFrame.getAlgorithmOn()) {
            tickTackToeGrid.algMakeMove();
        }
        this.showMessage("Your turn: " + message);
    }

    public void onMove(String[] data) {
        String player = data[0];
        String move = data[1];
        MoveResponse result = MoveResponse.valueOf(data[2]);
        if (result.equals(MoveResponse.TICKTACKTOE)) {
            this.showMessage(player + " made a move: " + move + " in " + result);
            tickTackToeGrid.updateGrid(Integer.parseInt(move), player);
            return;
        }
        this.showMessage(player + " made a move: " + move + " " + result);
    }

    public void onWin() {
        showMessage("Win");
        JOptionPane.showMessageDialog(this.mainFrame, "You won the game");
        Main.setGameType(GameType.ENDGAME);
    }

    public void onLose() {
        showMessage("Lose");
        JOptionPane.showMessageDialog(this.mainFrame, "You lost the game");
        Main.setGameType(GameType.ENDGAME);
    }

    public void onDraw() {
        showMessage("Draw");
        JOptionPane.showMessageDialog(this.mainFrame, "The game ended in a draw");
        Main.setGameType(GameType.ENDGAME);
    }

    public void onHelp(String message) {
        showMessage(message);
    }

    public void onError(String message) {
        showMessage(message);
    }

    public void showMessage(String message) {
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage("You", message);
    }

    public void onMessage(String message) {
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage(message);
    }
}