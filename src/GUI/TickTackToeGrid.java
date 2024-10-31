package src.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.Main;
import src.Telnet.Move;

public class TickTackToeGrid extends JPanel {
    private final JButton[][] grid;
    private boolean isYourTurn = true;  // Track whose turn it is
    private final Main main;

    public TickTackToeGrid(Main main) {
        this.main = main;
        int gridSize = 3;
        super.setLayout(new GridLayout(gridSize, gridSize));
        this.grid = new JButton[gridSize][gridSize];
        this.setBorder(BorderFactory.createTitledBorder("Tic Tac Toe"));
        fillGrid(gridSize);
    }

    public JButton[][] getGrid() {
        return this.grid;
    }

    public void updateGrid(int index, String player) {
        System.out.println("Updating grid with player: " + player);
        int gridSize = 3;
        int row = index / gridSize;
        int col = index % gridSize;
        JButton cell = this.grid[row][col];
        cell.setText(player.equals(main.getPlayerName()) ? "X" : "O");
        System.out.println(cell.getText());
        cell.setForeground(player.equals(main.getPlayerName()) ? Color.RED : Color.BLUE);
    }

    // Initialize Tic-Tac-Toe grid
    @SuppressWarnings("unused")
    private void fillGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JButton cell = new JButton();
                
                // Set button style
                cell.setFont(new Font("Arial", Font.BOLD, 60));
                cell.setFocusPainted(false);

                // Set action listener to toggle between X and O
                cell.addActionListener((ActionEvent e) -> {
                    if (cell.getText().isEmpty()) {  // Only allow placing on empty cells
                        if (isYourTurn) {
                            // cell.setText("X");
                            // cell.setForeground(Color.RED);
                            for (int i = 0; i < gridSize; i++) {
                                for (int j = 0; j < gridSize; j++) {
                                    if (this.grid[i][j].equals(cell)) {
                                        int index = i * gridSize + j;
                                        System.out.println("Index: " + index);
                                        this.main.sendMove(new Move(index));
                                    }
                                }
                            }
                        }
                    }
                });
                
                // Add the cell to the grid
                this.add(cell);
                this.grid[row][col] = cell;
            }
        }
    }

    public void setYourTurn(boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
    }
}
