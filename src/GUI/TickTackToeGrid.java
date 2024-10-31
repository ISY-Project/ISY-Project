package src.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.ALG.Minimax;
import src.Main;
import src.Telnet.Move;

public class TickTackToeGrid extends PlayerGrid {
    private final JButton[][] grid;
    // private final engine engine;

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

    public void updateGrid(int index, String player) {
        System.out.println("Updating grid with player: " + player);
        int gridSize = 3;
        int row = index / gridSize;
        int col = index % gridSize;
        JButton cell = this.grid[row][col];
        String playerName = engine.getPlayerName();
        cell.setText(player.equals(playerName) ? "X" : "O");
        System.out.println(cell.getText());
        cell.setForeground(player.equals(playerName) ? Color.RED : Color.BLUE);
    }

    // Initialize Tic-Tac-Toe grid
    private void fillGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JButton cell = new JButton();
                
                // Set button style
                cell.setFont(new Font("Arial", Font.BOLD, 60));
                cell.setFocusPainted(false);

                if (engine.getAlgorithmOn()) {
                    cell.setEnabled(false);
                }

                // Set action listener to toggle between X and O
                cell.addActionListener((ActionEvent e) -> {
                    if (!engine.getAlgorithmOn()) {
                        if (cell.getText().isEmpty()) {  // Only allow placing on empty cells
                            if (engine.getIsPlayerTurn()) {
                                // cell.setText("X");
                                // cell.setForeground(Color.RED);
                                for (int i = 0; i < gridSize; i++) {
                                    for (int j = 0; j < gridSize; j++) {
                                        if (this.grid[i][j].equals(cell)) {
                                            int index = i * gridSize + j;
                                            System.out.println("Index: " + index);
                                            this.main.sendMove(new Move(index));
                                            this.engine.setIsPlayerTurn(false);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Algorithm is on, cannot place move");
                    }
                });
                
                // Add the cell to the grid
                this.add(cell);
                this.grid[row][col] = cell;
            }
        }
    }

    public void algMakeMove() {
        // TODO: Use the engine to get move info?
        int gridSize = 3;
        char[] new_grid = new char[gridSize * gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (this.grid[i][j].getText().isEmpty()) {
                    new_grid[i * gridSize + j] = ' ';
                } else {
                    new_grid[i * gridSize + j] = this.grid[i][j].getText().charAt(0);
                }
            }
        }
        System.out.println(Arrays.toString(new_grid));
        System.out.println("Algorithm making move " + Minimax.getBestMove(new_grid, 'X'));
        // TODO: Handler should send the move to the server
        this.main.sendMove(new Move(Minimax.getBestMove(new_grid, 'X')));
        this.engine.setIsPlayerTurn(false);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
