package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OpponentGrid extends JPanel {
    private final JButton[][] grid;

    public OpponentGrid(int gridSize) {
        super(new GridLayout(gridSize, gridSize));
        this.grid = new JButton[gridSize][gridSize];
        this.setBorder(BorderFactory.createTitledBorder("Opponent's Grid"));
        fillGrid(gridSize);
    }

    public JButton[][] getGrid() {
        return this.grid;
    }

    // Initialize opponent grid (simplified)
    @SuppressWarnings("unused")
    private void fillGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                var cell = new GridCell();
                cell.addActionListener((ActionEvent e) -> {
                    // When a player clicks on their grid to place a ship
                    // TODO - Send shot message to the server and wait for response
                    if (Math.random() < 0.5) {
                        // onHit
                        cell.setColor(Color.RED);
                    } else {
                        // onMiss
                        cell.setColor(Color.GRAY);
                    }
                });
                this.add(cell);
                this.grid[row][col] = cell;
            }
        }
    }
}
