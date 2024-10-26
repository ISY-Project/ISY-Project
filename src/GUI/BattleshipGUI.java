package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

// TODO - Add a way to join an leave the game and start the game

public class BattleshipGUI extends JFrame {
    private static final int gridSize = 8;
    private Ships ships; // Ships available to be placed
    private PlayerGrid playerGrid;
    private OpponentGrid opponentGrid;
    private InfoPanel infoPanel;
    private JPanel chatBox;

    public PlayerGrid getPlayerGrid() {
        return playerGrid;
    }

    public OpponentGrid getOpponentGrid() {
        return opponentGrid;
    }

    public JPanel getChatBox() {
        return chatBox;
    }

    public BattleshipGUI() {
        int[] initialShipSizes = { 2, 3, 3, 4, 5 }; // Ship sizes available to be placed
        this.ships = new Ships(initialShipSizes);

        setTitle("Battleship Game");
        setSize(1100, 400);
        setLayout(new BorderLayout());

        this.playerGrid = new PlayerGrid(gridSize, ships);
        this.opponentGrid = new OpponentGrid(gridSize);
        this.infoPanel = new InfoPanel(ships, playerGrid, opponentGrid);
        this.chatBox = new ChatBox();

        // Add components to the frame
        add(this.playerGrid, BorderLayout.WEST);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.opponentGrid, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BattleshipGUI();
    }
}
