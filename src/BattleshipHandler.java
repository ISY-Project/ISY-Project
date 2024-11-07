package src;

import javax.swing.JOptionPane;
import src.GUI.BattleshipGUI;
import src.GUI.MainFrame;
import src.GUI.OpponentGrid;
import src.GUI.PlayerGrid;
import src.GUI.Screens;
import src.GUI.Ship;
import src.GameEngineBattleship.GameMasterBattleship;
import src.Telnet.EventHandler;
import src.Telnet.Place;
import src.Telnet.Responses.MoveResponse;
import src.Telnet.TelnetClient;

public class BattleshipHandler extends EventHandler {
    private final TelnetClient client;
    private final BattleshipGUI battleshipGUI;
    private final PlayerGrid playerGrid;
    private final OpponentGrid opponentGrid;
    private final MainFrame mainFrame;
    private final GameMasterBattleship engine;

    public BattleshipHandler(GameMasterBattleship engine) {
        super(GameType.BATTLESHIP);
        this.mainFrame = Main.getMainFrame();
        this.battleshipGUI = mainFrame.getBattleshipGUI();
        this.playerGrid = battleshipGUI.getPlayerGrid();
        this.opponentGrid = battleshipGUI.getOpponentGrid();
        this.engine = engine;
        this.client = Main.getTelnetClient();
    }

    public void onChallenge(String playerName, int game, int gameNumber) {
        this.showMessage(playerName + " has challenged you to a game of " + game + " with game number " + gameNumber);
    }

    public void onCancel(int gameNumber) {
        this.showMessage("Game " + gameNumber + " has been canceled");
    }

    public void onMatch() {
        Main.setGameType(GameType.BATTLESHIP);
        Main.toggleBattleshipGrid();
        if (Main.getGameType() == GameType.NONE) {
            // If we didn't start the game ourselves, we move to it and turn on comp.
            Main.getMainFrame().showScreen(Screens.BATTLESHIP);
            mainFrame.setAlgorithmOn(true);
        }
        playerGrid.resetGrid();
        opponentGrid.resetGrid();
        String message2 = "Match started"; // TODO: show opponent name.
        battleshipGUI.getInfoPanel().setYourTurnLabel(message2);
        this.showMessage(message2);
    }

    public void onYourTurn(String message) {
        mainFrame.setIsPlayerTurn(true);
        if (mainFrame.getAlgorithmOn()) {
            // for (@SuppressWarnings("unused") int i: engine.getShips()){
                placeShip();
            // }
        }
    }

    private void placeShip() {
        int begin, end, size;
        int[] coords = engine.placeRandomShip();
        begin = coords[0];
        end = coords[1];
        size = end - begin + 1;
        placeShip(begin, end, size);
    }

    private void placeShip(int begin, int end, int size) {
        int row = begin / 8;
        int col = begin % 8;
        boolean isVertical = (begin - end) % 8 == 0;
        Ship ship = new Ship(size, isVertical);
        this.playerGrid.placeShip(row, col, ship);
        this.client.sendMessage(new Place(begin, end).get());
        this.battleshipGUI.getChatBox().addMessage("Info", "Placed ship of size " + size + " at " + begin + " through " + end);
    }

    public void onMove(String player, String move, MoveResponse result) {
        this.showMessage(player + " made a move: " + move + " " + result);
        if (result.equals(MoveResponse.BOEM)) {
            updatePlayerTurnLabel(player);
            return;
        } else if (result.equals(MoveResponse.PLONS)) {
            updatePlayerTurnLabel(player);
            return;
        }
    }

    private void updatePlayerTurnLabel(String player) {
        if (player.equals(Main.getPlayerName())) {
            battleshipGUI.getInfoPanel().setYourTurnLabel("Opponent's turn");
        } else {
            battleshipGUI.getInfoPanel().setYourTurnLabel("It's Your turn");
        }
    }

    public void onWin() {
        String msg = "You won the game";
        showMessage(msg);
        JOptionPane.showMessageDialog(this.mainFrame, msg);
        battleshipGUI.getInfoPanel().setYourTurnLabel(msg);
        Main.setGameType(GameType.ENDGAME);
    }

    public void onLose() {
        String msg = "You lost the game";
        showMessage(msg);
        JOptionPane.showMessageDialog(this.mainFrame, msg);
        battleshipGUI.getInfoPanel().setYourTurnLabel(msg);
        Main.setGameType(GameType.ENDGAME);
    }

    public void onDraw() {
        String msg = "The game ended in a draw";
        showMessage(msg);
        JOptionPane.showMessageDialog(this.mainFrame, msg);
        battleshipGUI.getInfoPanel().setYourTurnLabel(msg);
        Main.setGameType(GameType.ENDGAME);
    }

    public void onHelp(String message) {
        showMessage(message);
    }

    public void onError(String message) {
        showMessage(message);
    }

    public void showMessage(String message) {
        this.battleshipGUI.getChatBox().addMessage("Server", message);
    }

    public void onMessage(String message) {
        this.battleshipGUI.getChatBox().addMessage(message);
    }
}