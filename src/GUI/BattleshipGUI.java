package src.GUI;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import src.Main;


public class BattleshipGUI extends JPanel implements IsyGui {
    private static final int GRIDSIZE = 8;
    private final Ships ships;
    private final PlayerGrid playerGrid;
    private final OpponentGrid opponentGrid;
    private final InfoPanel infoPanel;
    private final ChatBox chatBox;
    private final AlgCheckbox algorithmToggle;

    public PlayerGrid getPlayerGrid() {
        return playerGrid;
    }

    public OpponentGrid getOpponentGrid() {
        return opponentGrid;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public BattleshipGUI() {
        int[] initialShipSizes = { 2, 3, 3, 4, 5 };
        this.ships = new Ships(initialShipSizes);

        setLayout(new BorderLayout());

        this.playerGrid = new BattleshipGrid(GRIDSIZE, ships);
        this.opponentGrid = new OpponentGrid(GRIDSIZE);
        this.infoPanel = new InfoPanel(ships, playerGrid, opponentGrid);
        this.chatBox = new ChatBox();
        this.algorithmToggle = new AlgCheckbox(Main.getBattleshipEngine(), "Use Algorithm");

        add(this.playerGrid, BorderLayout.WEST);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.opponentGrid, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);
        add(this.algorithmToggle, BorderLayout.NORTH);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
        this.algorithmToggle.addActionListener(actionListener -> {
            if (this.algorithmToggle.isSelected()) {
                disableGrid();
                this.algorithmToggle.setEnabled(false);
            } else {
                enableGrid();
            }
        });
    }

    private void enableGrid() {
        setGrid(true);
    }

    private void disableGrid() {
        setGrid(false);
    }

    private void setGrid(boolean value) {
        for (JButton[] row : this.playerGrid.getGrid()) {
            for (JButton cell : row) {
                cell.setEnabled(value);
            }
        }
        for (JButton[] row : this.opponentGrid.getGrid()) {
            for (JButton cell : row) {
                cell.setEnabled(value);
            }
        }
    }
}
