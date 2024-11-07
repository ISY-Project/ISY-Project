package src;

import src.Enums.PlayingGameState;
import src.Enums.Screens;
import src.GUI.Grids.TickTackToeGrid;
import src.GUI.Views.MainFrame;
import src.Telnet.EventHandler;
import src.Telnet.Responses.MoveResponse;

public class TTTHandler extends EventHandler {
    private final TickTackToeGrid tickTackToeGrid;
    private final MainFrame mainFrame;

    public TTTHandler(TickTackToeGrid tickTackToeGrid) {
        super(PlayingGameState.TTT);
        this.tickTackToeGrid = tickTackToeGrid;
        this.mainFrame = Main.getMainFrame();
    }

    public void onChallenge(String playerName, int game, int gameNumber) {
        this.showServerMessage(playerName + " has challenged you to a game of " + game + " with game number " + gameNumber);
    }

    public void onCancel(int gameNumber) {
        this.showServerMessage("Game " + gameNumber + " has been canceled");
    }

    public void onMatch() {
        tickTackToeGrid.setFirstMove(true);
        tickTackToeGrid.setIsPlayerX(false);
        if (Main.getGameType() == PlayingGameState.FIRSTBOOT) {
            // If we didn't start the game ourselves, we move to it and turn on comp.
            System.out.println("forced starting TTT game");
            mainFrame.setAlgorithmOn(true);
        }
        Main.getMainFrame().showScreen(Screens.TTT);
        Main.setGameType(PlayingGameState.TTT);
        Main.toggleTTTGrid();
        tickTackToeGrid.resetGrid();
        this.showServerMessage("Match started");
    }

    public void onYourTurn(String message) {
        mainFrame.setIsPlayerTurn(true);
        if (tickTackToeGrid.isFirstMove()) {
            tickTackToeGrid.setIsPlayerX(true);
            mainFrame.getTickTackToe().getInformationPanel().setYourTurnLabel("It's Your turn");
            this.showServerMessage("You are player X");
            this.showServerMessage("It's your turn");
        }
        mainFrame.getTickTackToe().getInformationPanel().setPlayerChar(tickTackToeGrid.isIsPlayerX() ? "X" : "O");
        if (mainFrame.getAlgorithmOn()) {
            tickTackToeGrid.algMakeMove();
        }
    }

    public void onMove(String[] data) {
        if (tickTackToeGrid.isFirstMove()) {
            tickTackToeGrid.setFirstMove(false);
            if (!tickTackToeGrid.isIsPlayerX()) {
                this.showServerMessage("It's your turn");
            }
        }
        String player = data[0];
        String move = data[1];
        MoveResponse result = MoveResponse.valueOf(data[2]);
        if (result.equals(MoveResponse.TICKTACKTOE)) {
            // this.showServerMessage(player + " made a move: " + move + " in " + result); 
            // Niet nodig om te laten zien
            tickTackToeGrid.updateGrid(Integer.parseInt(move), player);
            return;
        }
        this.showServerMessage(player + " made a move: " + move + " " + result);
    }

    public void onWin() {
        Main.setGameType(PlayingGameState.ENDGAME);
        Main.toggleTTTGrid();
        this.showServerMessage("You won the game");
        // JOptionPane.showMessageDialog(this.mainFrame, "You won the game");
    }

    public void onLose() {
        Main.setGameType(PlayingGameState.ENDGAME);
        Main.toggleTTTGrid();
        this.showServerMessage("You lost the game");
        // JOptionPane.showMessageDialog(this.mainFrame, "You lost the game");
    }

    public void onDraw() {
        Main.setGameType(PlayingGameState.ENDGAME);
        Main.toggleTTTGrid();
        this.showServerMessage("The game ended in a draw");
        // JOptionPane.showMessageDialog(this.mainFrame, "The game ended in a draw");
    }

    public void onHelp(String message) {
        this.showServerMessage(message);
    }

    public void onError(String message) {
        this.showServerMessage(message);
    }

    public void showMessage(String message) {
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage("You", message);
    }

    public void showServerMessage(String message) {
        System.out.println("Received: " + message);
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage("Server", message);
    }

    public void onMessage(String message) {
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage(message);
    }

}