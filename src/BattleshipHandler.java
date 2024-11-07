package src;

import java.awt.Color;
import src.GUI.BattleshipGUI;
import src.GUI.MainFrame;
import src.GUI.OpponentGrid;
import src.GUI.PlayerGrid;
import src.GUI.Screens;
import src.GUI.Ship;
import src.GameEngineBattleship.GameMasterBattleship;
import src.Telnet.EventHandler;
import src.Telnet.Move;
import src.Telnet.Place;
import src.Telnet.Responses.MoveResponse;
import src.Telnet.TelnetClient;

public class BattleshipHandler extends EventHandler {
    private final TelnetClient client;
    private final BattleshipGUI battleshipGUI;
    private final PlayerGrid playerGrid;
    private final OpponentGrid opponentGrid;
    private final MainFrame mainFrame;
    private GameMasterBattleship engine;
    private boolean placeStage = true;

    public BattleshipHandler() {
        super(GameType.Battleship);
        this.mainFrame = Main.getMainFrame();
        this.battleshipGUI = mainFrame.getBattleshipGUI();
        this.playerGrid = battleshipGUI.getPlayerGrid();
        this.opponentGrid = battleshipGUI.getOpponentGrid();
        this.engine = createBattleshipMaster();
        this.client = Main.getTelnetClient();
    }

    private GameMasterBattleship createBattleshipMaster() {
        return new GameMasterBattleship(
        new int[] { 8, 8 },
        new int[] { 6, 4, 3, 2 },
        new boolean[] { false });
    }

    public void onChallenge(String playerName, int game, int gameNumber) {
        this.showMessage(playerName + " has challenged you to a game of " + game + " with game number " + gameNumber);
    }

    public void onCancel(int gameNumber) {
        this.showMessage("Game " + gameNumber + " has been canceled");
    }

    public void onMatch() {
        if (Main.getGameType() == GameType.FIRSTBOOT) {
            // If we didn't start the game ourselves, we move to it and turn on comp.
            System.out.println("forced starting battle game");
            Main.getMainFrame().showScreen(Screens.BATTLESHIP);
            mainFrame.setAlgorithmOn(true);
        }
        Main.setGameType(GameType.Battleship);
        Main.toggleBattleshipGrid();
        playerGrid.resetGrid();
        opponentGrid.resetGrid();
        String message2 = "Match started"; // TODO: show opponent name.
        battleshipGUI.getInfoPanel().setYourTurnLabel(message2);
        this.showMessage(message2);
    }

    public void onYourTurn(String message) {
        mainFrame.setIsPlayerTurn(true);
        if (placeStage) {
            if (mainFrame.getAlgorithmOn()) {
                for (@SuppressWarnings("unused") int i: engine.getShips()){
                    placeShip();
                }
                placeStage = false;
            }
        } else{
            shoot();
        }
    }

    private void shoot() {
        long cell = engine.getOptimalShot();
        this.client.sendMessage(new Move(cell).get());
        this.battleshipGUI.getChatBox().addMessage("Info", "Shooting at " + cell);
    }

    private void placeShip() {
        int begin, end, size;
        int[] coords = engine.placeRandomShip();
        begin = coords[0];
        end = coords[coords.length-1];
        boolean isVertical = (begin - end) % playerGrid.getGridSize() == 0;
        size = calculateSize(begin, end, isVertical);
        placeShip(begin, end, size);
    }

    private int calculateSize(int begin, int end, boolean isVertical) {
        int size;
        if (isVertical) {
            size = (end - begin) / playerGrid.getGridSize();
        } else {
            size = (end - begin);
        }
        return size + 1;
    }

    private void placeShip(int begin, int end, int size) {
        int row = begin / playerGrid.getGridSize();
        int col = begin % playerGrid.getGridSize();
        boolean isVertical = (begin - end) % 8 == 0;
        Ship ship = new Ship(size, isVertical);
        this.playerGrid.placeShip(row, col, ship);
        this.client.sendMessage(new Place(begin, end).get());
        this.battleshipGUI.getChatBox().addMessage("Info", "Placed ship of size " + size + " at " + begin + " through " + end);
    }

    public void onMove(String[] data) {
        String player = data[0];
        int move = Integer.parseInt(data[1]);
        MoveResponse result = MoveResponse.valueOf(data[2]);
        this.showMessage(player + " made a move: " + move + " " + result);
        if (player.equals(Main.getPlayerName())){
            if (result.equals(MoveResponse.BOEM)) {
                updatePlayerTurnLabel(player);
                this.battleshipGUI.getOpponentGrid().getGrid()[move / 8][move % 8].setBackground(Color.RED);
                engine.hit(move);
                return;
            } else if (result.equals(MoveResponse.PLONS)) {
                updatePlayerTurnLabel(player);
                this.battleshipGUI.getOpponentGrid().getGrid()[move / 8][move % 8].setBackground(Color.GRAY);
                engine.miss(move);
                return;
            } else if (result.equals(MoveResponse.GEZONKEN)) {
                this.battleshipGUI.getOpponentGrid().getGrid()[move / 8][move % 8].setBackground(Color.RED);
                int shipSize = Integer.parseInt(data[3]);
                engine.sink(move, shipSize); // CRITICAL: Fix shooting same spot twice after sink.
                return;
            }
        } else {
            // Tegenstander heeft geschoten
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
        // JOptionPane.showMessageDialog(this.mainFrame, msg);
        battleshipGUI.getInfoPanel().setYourTurnLabel(msg);
        Main.setGameType(GameType.ENDGAME);
        placeStage = true;
        engine = createBattleshipMaster();
    }

    public void onLose() {
        String msg = "You lost the game";
        showMessage(msg);
        // JOptionPane.showMessageDialog(this.mainFrame, msg);
        battleshipGUI.getInfoPanel().setYourTurnLabel(msg);
        Main.setGameType(GameType.ENDGAME);
        placeStage = true;
        engine = createBattleshipMaster();
    }

    public void onDraw() {
        String msg = "The game ended in a draw";
        showMessage(msg);
        // JOptionPane.showMessageDialog(this.mainFrame, msg);
        battleshipGUI.getInfoPanel().setYourTurnLabel(msg);
        Main.setGameType(GameType.ENDGAME);
        placeStage = true;
        engine = createBattleshipMaster();
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