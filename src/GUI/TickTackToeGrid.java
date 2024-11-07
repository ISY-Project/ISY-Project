package src.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.ALG.Minimax;
import src.GameType;
import src.Main;
import src.Telnet.Move;

public class TickTackToeGrid extends JPanel {
    private final JButton[][] grid;
    private final MainFrame mainFrame;
    private boolean isPlayerX = false;
    private boolean isFirstMove = true;

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    public TickTackToeGrid(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        int gridSize = 3;
        super.setLayout(new GridLayout(gridSize, gridSize));
        this.grid = new JButton[gridSize][gridSize];
        this.setBorder(BorderFactory.createTitledBorder("Tic Tac Toe"));
        fillGrid(gridSize);
    }

    public JButton[][] getGrid() {
        return this.grid;
    }

    public void clearGrid() {
        for (JButton[] row : this.grid) {
            for (JButton cell : row) {
                cell.setText("");
            }
        }
    }

    public void enableGrid() {
        for (JButton[] row : this.grid) {
            for (JButton cell : row) {
                cell.setEnabled(true);
            }
        }
    }

    public void disableGrid() {
        for (JButton[] row : this.grid) {
            for (JButton cell : row) {
                cell.setEnabled(false);
            }
        }
    }

    public void updateGrid(int index, String player) {
        System.out.println("Updating grid with player: " + player);
        int gridSize = 3;
        int row = index / gridSize;
        int col = index % gridSize;
        JButton cell = this.grid[row][col];
        if (isPlayerX) {
            cell.setText(player.equals(Main.getPlayerName()) ? "X" : "O");
            cell.setForeground(player.equals(Main.getPlayerName()) ? Color.RED : Color.BLUE);
        } else {
            cell.setText(player.equals(Main.getPlayerName()) ? "O" : "X");
            cell.setForeground(player.equals(Main.getPlayerName()) ? Color.BLUE : Color.RED);
        }
        if (player.equals(Main.getPlayerName())) {
            mainFrame.getTickTackToe().getInformationPanel().setYourTurnLabel("Opponent's turn");
        } else {
            mainFrame.getTickTackToe().getInformationPanel().setYourTurnLabel("Your turn");
        }
    }

    // Initialize Tic-Tac-Toe grid
    private void fillGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JButton cell = new JButton();
                
                // Set button style
                cell.setFont(new Font("Arial", Font.BOLD, 60));
                cell.setFocusPainted(false);

                if (mainFrame.getAlgorithmOn()) {
                    cell.setEnabled(false);
                }

                // Set action listener to toggle between X and O
                cell.addActionListener((ActionEvent e) -> {
                    if (!mainFrame.getAlgorithmOn()) {
                        if (cell.getText().isEmpty()) {  // Only allow placing on empty cells
                            if (mainFrame.getIsPlayerTurn()) {
                                // cell.setText("X");
                                // cell.setForeground(Color.RED);
                                for (int i = 0; i < gridSize; i++) {
                                    for (int j = 0; j < gridSize; j++) {
                                        if (this.grid[i][j].equals(cell)) {
                                            int index = i * gridSize + j;
                                            System.out.println("Index: " + index);
                                            Main.getTelnetClient().sendMessage((new Move(index).get()));
                                            this.mainFrame.setIsPlayerTurn(false);
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
        if (Main.getGameType() != GameType.TTT) {
            disableGrid();
        } else {
            enableGrid();
        }
        
    }

    public void algMakeMove() {
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
        int bestMove = Minimax.getBestMove(new_grid, isPlayerX ? 'X' : 'O');
        System.out.println("Algorithm making move " + bestMove);
        Main.getTelnetClient().sendMessage((new Move(bestMove).get()));
        this.mainFrame.setIsPlayerTurn(false);
    }

    public boolean isIsPlayerX() {
        return isPlayerX;
    }

    public void setIsPlayerX(boolean isPlayerX) {
        this.isPlayerX = isPlayerX;
    }
}
