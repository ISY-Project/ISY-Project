package src.GUI.Views;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import src.GUI.Components.ChatBox;
import src.GUI.Grids.OpponentGrid;
import src.GUI.Grids.PlayerGrid;
import src.Helpers.Ships;


public class BattleshipGUI extends JPanel {
    private static final int GRIDSIZE = 8;
    private final Ships ships;
    private final PlayerGrid playerGrid;
    private final OpponentGrid opponentGrid;
    private final InfoPanel infoPanel;

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

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
        int[] initialShipSizes = { 2, 3, 3, 4, 5 };
        this.ships = new Ships(initialShipSizes);

        setLayout(new BorderLayout());

        this.playerGrid = new PlayerGrid(GRIDSIZE, ships);
        this.opponentGrid = new OpponentGrid(GRIDSIZE);
        this.infoPanel = new InfoPanel(ships);
        this.chatBox = new ChatBox();

        add(this.playerGrid, BorderLayout.WEST);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.opponentGrid, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
    }
}
