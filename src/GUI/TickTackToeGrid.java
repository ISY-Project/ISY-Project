package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TickTackToeGrid extends JPanel {
    private JButton[][] grid;
    private boolean isXTurn = true;  // Track whose turn it is

    public TickTackToeGrid() {
        int gridSize = 3;
        super.setLayout(new GridLayout(gridSize, gridSize));
        this.grid = new JButton[gridSize][gridSize];
        this.setBorder(BorderFactory.createTitledBorder("Tic Tac Toe"));
        fillGrid(gridSize);
    }

    public JButton[][] getGrid() {
        return this.grid;
    }

    // Initialize Tic-Tac-Toe grid
    private void fillGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JButton cell = new JButton();
                
                // Set button style
                cell.setFont(new Font("Arial", Font.BOLD, 60));
                cell.setFocusPainted(false);

                // Set action listener to toggle between X and O
                cell.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cell.getText().isEmpty()) {  // Only allow placing on empty cells
                            if (isXTurn) {
                                cell.setText("X");
                                cell.setForeground(Color.RED);
                            } else {
                                cell.setText("O");
                                cell.setForeground(Color.BLUE);
                            }
                            isXTurn = !isXTurn;  // Toggle turn
                        }
                    }
                });
                
                // Add the cell to the grid
                this.add(cell);
                this.grid[row][col] = cell;
            }
        }
    }

    public void setXTurn(boolean isXTurn) {
        this.isXTurn = isXTurn;
    }
}
