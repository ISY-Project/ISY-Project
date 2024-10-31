package src.GUI;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayerGrid extends JPanel {
    protected final JButton[][] grid;

    public PlayerGrid(int gridSize) {
        super(new GridLayout(gridSize, gridSize));
        this.grid = new JButton[gridSize][gridSize];
        this.setBorder(BorderFactory.createTitledBorder("Player's Grid"));
    }

    public JButton[][] getGrid() {
        return this.grid;
    }
}
