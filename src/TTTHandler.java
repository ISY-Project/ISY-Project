package src;

import src.GUI.MainFrame;
import src.GUI.Screens;
import src.GUI.TickTackToeGrid;
import src.Telnet.EventHandler;
import src.Telnet.Responses.MoveResponse;

public class TTTHandler extends EventHandler {
    private final TickTackToeGrid tickTackToeGrid;
    private final MainFrame mainFrame;

    public TTTHandler(TickTackToeGrid tickTackToeGrid) {
        super(GameType.TTT);
        // System.out.println("TTTHandler created");
        this.tickTackToeGrid = tickTackToeGrid;
        this.mainFrame = Main.getMainFrame();
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
        tickTackToeGrid.setFirstMove(true);
        tickTackToeGrid.setIsPlayerX(false);
        if (Main.getGameType() == GameType.NONE) {
            // If we didn't start the game ourselves, we move to it and turn on comp.
            System.out.println("forced starting TTT game");
            Main.getMainFrame().showScreen(Screens.TTT);
            mainFrame.setAlgorithmOn(true);
        }
        Main.setGameType(GameType.TTT);
        Main.toggleTTTGrid();
        tickTackToeGrid.clearGrid();
        this.showServerMessage("Match started");
    }

    @Override
    public void onYourTurn(String message) {
        // System.out.println("Your turn TTT");
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

    @Override
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

    @Override
    public void onWin() {
        String msg = "You won the game";
        Main.setGameType(GameType.ENDGAME);
        Main.toggleTTTGrid();
        Main.getMainFrame().getTickTackToe().getInformationPanel().setYourTurnLabel(msg);
        this.showServerMessage(msg);
        // JOptionPane.showMessageDialog(this.mainFrame, "You won the game");
    }

    @Override
    public void onLose() {
        String msg = "You lost the game";
        Main.setGameType(GameType.ENDGAME);
        Main.toggleTTTGrid();
        Main.getMainFrame().getTickTackToe().getInformationPanel().setYourTurnLabel(msg);
        this.showServerMessage(msg);
        // JOptionPane.showMessageDialog(this.mainFrame, "You lost the game");
    }

    @Override
    public void onDraw() {
        String msg = "The game ended in a draw";
        Main.setGameType(GameType.ENDGAME);
        Main.toggleTTTGrid();
        Main.getMainFrame().getTickTackToe().getInformationPanel().setYourTurnLabel(msg);
        this.showServerMessage(msg);
        // JOptionPane.showMessageDialog(this.mainFrame, "The game ended in a draw");
    }

    @Override
    public void onHelp(String message) {
        this.showServerMessage(message);
    }

    @Override
    public void onError(String message) {
        this.showServerMessage(message);
    }

    @Override
    public void showMessage(String message) {
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage("You", message);
    }

    public void showServerMessage(String message) {
        System.out.println("Received: " + message);
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage("Server", message);
    }

    @Override
    public void onMessage(String message) {
        Main.getMainFrame().getTickTackToe().getChatBox().addMessage(message);
    }

}