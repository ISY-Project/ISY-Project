package GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;

// TODO - Add a way to join an leave the game and start the game

public class BattleshipGUI extends JPanel {
    private static final int GRIDSIZE = 8;
    private final Ships ships; // Ships available to be placed
    private final PlayerGrid playerGrid;
    private final OpponentGrid opponentGrid;
    private final InfoPanel infoPanel;
    private final ChatBox chatBox;

    public PlayerGrid getPlayerGrid() {
        return playerGrid;
    }

    public OpponentGrid getOpponentGrid() {
        return opponentGrid;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public BattleshipGUI(MainFrame mainFrame) {
        int[] initialShipSizes = { 2, 3, 3, 4, 5 }; // Ship sizes available to be placed
        this.ships = new Ships(initialShipSizes);

        setLayout(new BorderLayout());

        this.playerGrid = new PlayerGrid(GRIDSIZE, ships);
        this.opponentGrid = new OpponentGrid(GRIDSIZE);
        this.infoPanel = new InfoPanel(ships, playerGrid, opponentGrid);
        this.chatBox = new ChatBox();

        // Add components to the frame
        add(this.playerGrid, BorderLayout.WEST);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.opponentGrid, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
    }
}
