package src.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;


public class TickTackToeGrid extends PlayerGrid {
    private final JButton[][] grid;

    public TickTackToeGrid() {
        super(3);
        int gridSize = 3;
        super.setLayout(new GridLayout(gridSize, gridSize));
        this.grid = new JButton[gridSize][gridSize];
        this.setBorder(BorderFactory.createTitledBorder("Tic Tac Toe"));
        fillGrid(gridSize);
    }

    public JButton[][] getGrid() {
        return this.grid;
    }

    public void updateCell(int index, char player) {
        int gridSize = 3;
        int row = index / gridSize;
        int col = index % gridSize;
        JButton cell = this.grid[row][col];
        cell.setText(player == 'X' ? "X" : "O");
        cell.setForeground(player == 'X' ? Color.RED : Color.BLUE);
    }

    // Initialize Tic-Tac-Toe grid
    private void fillGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JButton cell = new JButton();
                
                // Set button style
                cell.setFont(new Font("Arial", Font.BOLD, 60));
                cell.setFocusPainted(false);
                cell.setEnabled(true);
                // Add the cell to the grid
                this.add(cell);
                this.grid[row][col] = cell;
            }
        }
    }
}
