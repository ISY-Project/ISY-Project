package src.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import src.Main;
import src.Telnet.Move;
import src.Telnet.TelnetClient;


public class TickTackToeGrid extends PlayerGrid {
    private final JButton[][] grid;
    private boolean isXTurn = true;
    private final TelnetClient client = Main.getClient();

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

    private void addCellListener(JButton cell, int x, int y) {
        cell.addActionListener(e -> {
            // Send the move to the server
            // this.engine.sendMove(index);
            int location = x*3 + y;
            if (cell.getText().isEmpty()) {  // Only allow placing on empty cells, and maintain colour.
                String playerMove = new Move(location).get();
                if (isXTurn) {
                    cell.setText("X");
                    cell.setForeground(Color.RED);
                    client.sendMessage(playerMove);
                } else {
                    cell.setText("O");
                    cell.setForeground(Color.BLUE);
                    client.sendMessage(playerMove);
                }
                isXTurn = !isXTurn;  // Toggle turn
            }
        });
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
                addCellListener(cell, row, col);
                // Add the cell to the grid
                this.add(cell);
                this.grid[row][col] = cell;
            }
        }
    }
}
