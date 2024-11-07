package src.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlayerGrid extends JPanel {
    private final JButton[][] grid;
    private final Ships ships;

    public PlayerGrid(int gridSize, Ships ships) {
        super(new GridLayout(gridSize, gridSize));
        this.grid = new JButton[gridSize][gridSize];
        this.ships = ships;
        this.setBorder(BorderFactory.createTitledBorder("Player's Grid"));
        fillGrid(gridSize);
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
                cell.setBackground(Color.blue);
            }
        }
    }

    public void disableGrid() {
        for (var row : this.grid) {
            for (var cell : row) {
                cell.setEnabled(false);
                cell.setBackground(Color.gray);
            }
        }
    }

    // Initialize player grid with buttons
    @SuppressWarnings("unused")
    private void fillGrid(int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int finalRow = row;
                int finalCol = col;
                var cell = new GridCell();
                cell.setText(row * gridSize + col + "");
                cell.addActionListener((ActionEvent e) -> {
                    // When a player clicks on their grid to place a ship
                    System.out.println("Clicked on cell: " + finalRow + ", " + finalCol);
                    String placingMessage = "What size ship do you want to place?\nAvailable sizes:"
                            + Arrays.toString(ships.ShipSizes());
                    String chosenShipSize = JOptionPane.showInputDialog(placingMessage);
                    int selectedShipSize;
                    try {
                        selectedShipSize = Integer.parseInt(chosenShipSize);
                        System.out.println("Selected ship size: " + selectedShipSize);
                    } catch (NumberFormatException err) {
                        JOptionPane.showMessageDialog(cell, "Invalid ship size");
                        System.out.println("Invalid ship size: " + chosenShipSize);
                        return;
                    }
                    
                    Ship selectedShip = ships.getShipBySize(selectedShipSize);
                    if (selectedShip == null) {
                        JOptionPane.showMessageDialog(cell, "Invalid ship size");
                        return;
                    }
                    
                    // TODO - Wait for response from the engine
                    // TODO - If the engine accepts the placement, place the ship on the grid
                    if (!validateGridBorderPlacement(finalRow, finalCol, selectedShip)) {
                        JOptionPane.showMessageDialog(cell, "Ship cannot be placed here.");
                        return;
                    }
                    placeShip(finalRow, finalCol, selectedShip);
                });
                this.add(cell);
                this.grid[row][col] = cell;
            }
        }
        disableGrid();
    }

    private void placeHorizontalShip(int row, int col, int size) {
        for (int i = 0; i < size; i++) {
            this.grid[row][col + i].setBackground(Color.GREEN);
        }
    }

    private void placeVerticalShip(int row, int col, int size) {
        for (int i = 0; i < size; i++) {
            this.grid[row + i][col].setBackground(Color.GREEN);
        }
    }

    public void placeShip(int row, int col, Ship ship) {
        if (ship.isVertical()) {
            placeVerticalShip(row, col, ship.getSize());
        } else {
            placeHorizontalShip(row, col, ship.getSize());
        }
    }

    private boolean validateGridBorderPlacement(int row, int col, Ship ship) {
        if (ship.isVertical()) {
            if (row + ship.getSize() > this.grid.length) {
                return false;
            }
        } else if (col + ship.getSize() > this.grid.length) {
            return false;
        }
        return true;
    }

}
