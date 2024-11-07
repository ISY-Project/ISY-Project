package src.GUI.Grids;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import src.GUI.Components.GridCell;

public class OpponentGrid extends BaseGrid {
    private final JButton[][] grid;

    public OpponentGrid(int gridSize) {
        super(gridSize);
        this.grid = new JButton[gridSize][gridSize];
        this.setBorder(BorderFactory.createTitledBorder("Opponent's Grid"));
        initializeGrid(gridSize);
    }

    public JButton[][] getGrid() {
        return this.grid;
    }

    public void resetGrid() {
        for (var row : this.grid) {
            for (var cell : row) {
                cell.setBackground(Color.blue);
            }
        }
    }

    public void enableGrid() {
        for (var row : this.grid) {
            for (var cell : row) {
                cell.setEnabled(true);
                cell.setBackground(Color.BLUE);
            }
        }
    }

    public void disableGrid() {
        for (var row : this.grid) {
            for (var cell : row) {
                cell.setEnabled(false);
                cell.setBackground(Color.GRAY);
            }
        }
    }
    // Initialize opponent grid (simplified)
    protected void initializeGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                var cell = new GridCell();
                cell.setText(row * gridSize + col + "");
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
        disableGrid();
    }
}
