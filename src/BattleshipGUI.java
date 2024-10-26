import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class BattleshipGUI extends JFrame {
    private static final int gridSize = 8;
    private JButton[][] playerGrid = new JButton[gridSize][gridSize];
    private JButton[][] opponentGrid = new JButton[gridSize][gridSize];
    private boolean[][] playerShips = new boolean[gridSize][gridSize];  // To track player ships
    private Ships ships;  // Ships available to be placed
    private JPanel playerPanel;
    private JPanel opponentPanel;
    private JPanel infoPanel;
    private JDialog chatBox;
    private boolean isVertical = true;  // Ship orientation (vertical/horizontal)
    private int selectedShipSize = 0;  // Track the size of the ship being dragged
    private int[] initialShipSizes = {2, 3, 3, 4, 5};  // Ship sizes available to be placed
    private Ship selectedShip;  // Ship being dragged

    public JButton getPlayerCell(int row, int col) {
        return playerGrid[row][col];
    }

    public JButton getOpponentCell(int row, int col) {
        return playerGrid[row][col];
    }

    public BattleshipGUI() {
        this.ships = new Ships(initialShipSizes);

        setTitle("Battleship Game");
        setSize(800, 425);
        setLayout(new BorderLayout());

        // Player's grid panel
        this.playerPanel = new JPanel(new GridLayout(gridSize, gridSize));
        this.playerPanel.setBorder(BorderFactory.createTitledBorder("Your Grid"));
        initializePlayerGrid(this.playerPanel);

        // Opponent's grid panel
        this.opponentPanel = new JPanel(new GridLayout(gridSize, gridSize));
        this.opponentPanel.setBorder(BorderFactory.createTitledBorder("Opponent Grid"));
        initializeOpponentGrid(this.opponentPanel);

        // information panel
        this.infoPanel = new JPanel(new GridLayout(3, 0));
        this.infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        initializeInfoPanel(this.infoPanel);

        // chat box
        // TODO: show the server chat messages and allow for sending messages.
        // chatBox = new JDialog();
        // this.infoPanel.add(chatBox);
        // this.chatBox.setBorder(BorderFactory.createTitledBorder("Information"));
        // initializeInfoPanel(this.chatBox);

        // Add components to the frame
        add(this.playerPanel, BorderLayout.WEST);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.opponentPanel, BorderLayout.EAST);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private int[][] createGrid(int size) {
        return createGrid(size, size);
    }

    private int[][] createGrid(int rows, int cols) {
        return new int[rows][cols];
    }

    private boolean validateGridBorderPlacement(int row, int col, int size, boolean isVertical) {
        if (isVertical) {
            if (row + size > gridSize) {return false;};
        } else if (col + size > gridSize) {return false;};
        return true;
    }

    private void placeShip(int row, int col, int size, boolean isVertical) {
        if (isVertical) {
            placeVerticalShip(row, col, size);
        } else {
            placeHorizontalShip(row, col, size);
        }
    }

    private void placeHorizontalShip(int row, int col, int size) {
        for (int i = 0; i < size; i++) {
            this.getPlayerCell(row, col + i).setBackground(Color.GREEN);
        }
    }

    private void placeVerticalShip(int row, int col, int size) {
        for (int i = 0; i < size; i++) {
            this.getPlayerCell(row + i, col).setBackground(Color.GREEN);
        }
    }

    private Ship getSelectedShip() {
        Ship selectedShip = null;
        for (var ship : ships.getShipSizes()) {
            if (ship == selectedShipSize) {
                selectedShip = new Ship(selectedShipSize);
            }
        }
        return selectedShip;
    }

    // Initialize player grid with buttons
    private void initializePlayerGrid(JPanel playerPanel) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                initializePlayerGridCell(playerPanel, row, col);
            }
        }
    }

    private void initializePlayerGridCell(JPanel playerPanel, int row, int col) {
        JButton cell = new JButton();
        cell.setBackground(Color.BLUE); // Water color
        final int finalRow = row;
        final int finalCol = col;

        var actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // When a player clicks on their grid to place a ship
                System.out.println("Clicked on cell: " + finalRow + ", " + finalCol);
                String placingMessage = "What size ship do you want to place?\nAvailable sizes:" + Arrays.toString(ships.getShipSizes());
                String chosenShipSize = JOptionPane.showInputDialog(placingMessage);
                try {
                    selectedShipSize = Integer.parseInt(chosenShipSize);
                    System.out.println("Selected ship size: " + selectedShipSize);
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(cell, "Invalid ship size");
                    System.out.println("Invalid ship size: " + chosenShipSize);
                    return;
                }

                selectedShip = getSelectedShip();
                if (selectedShip == null) {
                    JOptionPane.showMessageDialog(cell, "Invalid ship size");
                    return;
                }
                
                // TODO - Send ship placement message to the engine
                // TODO - Wait for response from the engine
                // TODO - If the engine accepts the placement, place the ship on the grid
                int shipSize = selectedShip.getSize();
                if (!validateGridBorderPlacement(finalRow, finalCol, shipSize, isVertical)) {
                    JOptionPane.showMessageDialog(cell, "Ship cannot be placed here.");
                    return;
                }
                placeShip(finalRow, finalCol, shipSize, isVertical);
            }

        };

        cell.addActionListener(actionListener);

        playerGrid[row][col] = cell;
        playerPanel.add(cell);
    }

    // Initialize opponent grid (simplified)
    private void initializeOpponentGrid(JPanel opponentPanel) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                InitializeOpponentGridCell(opponentPanel, row, col);
            }
        }
    }

    private void InitializeOpponentGridCell(JPanel opponentPanel, int row, int col) {
        JButton cell = new JButton();
        cell.setBackground(Color.BLUE); // Water color
        ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // When a player clicks on their grid to place a ship
                    // TODO - Send shot message to the server and wait for response
                    if (Math.random() < 0.5) {
                        onHit();
                    } else {
                        onMiss();
                }
            }
            
            private void onHit() {
                cell.setBackground(Color.RED); // Hit color
            }
            private void onMiss() {
                cell.setBackground(Color.GRAY); // Miss color
            }
        };
        cell.addActionListener(actionListener);

        opponentPanel.add(cell);
        opponentGrid[row][col] = cell;
    }

    private void initializeInfoPanel(JPanel infoPanel) {
        // Add buttons to the info panel

        JButton resetButton = new JButton("Reset Ships");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (var row : playerGrid) {
                    for (var cell : row) {
                        cell.setBackground(Color.BLUE);
                    }
                }
                for (var row : opponentGrid) {
                    for (var cell : row) {
                        cell.setBackground(Color.BLUE);
                    }
                }
            }
        });
        JButton rotateButton = new JButton("Rotate Ship");
        rotateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotateShip();
            }
        });

        // TODO - Add a way to join an leave the game and start the game and add a chat (for trash talking)

        infoPanel.add(resetButton);
        infoPanel.add(rotateButton);
        infoPanel.add(new JLabel("Select a ship to place on the grid:"));
        infoPanel.add(new JLabel("Avable Ships:" + Arrays.toString(ships.shipSizes)));
        // TODO - Add an way to update the show information about the game
    }

    // Rotate the currently selected ship
    private void rotateShip() {
        isVertical = !isVertical;  // Toggle orientation
        if (selectedShip != null) {
            String orientation = isVertical ? "Vertical" : "Horizontal";
            JOptionPane.showMessageDialog(this, "Rotated " + selectedShip.getSize() + " to " + orientation);
        }
    }

    // Place the selected ship on the player's grid
    private void placeShipOnGrid(int row, int col, int size, boolean isVertical) {
        if (selectedShip == null) {
            JOptionPane.showMessageDialog(this, "Please select a ship to place.");
            return;
        }

        // Check if the ship fits on the grid
        if (!canPlaceShip(row, col, selectedShipSize, isVertical)) {
            JOptionPane.showMessageDialog(this, "Ship cannot be placed here.");
            return;
        }

        // Place ship on the grid
        for (int i = 0; i < selectedShipSize; i++) {
            if (isVertical) {
                playerGrid[row + i][col].setBackground(Color.GRAY);
            } else {
                playerGrid[row][col + i].setBackground(Color.GRAY);
            }
        }

        // After placing, reset the selected ship
        selectedShip = null;
        selectedShipSize = 0;
    }

    // Check if the ship can be placed at the given location without overlapping or going out of bounds
    private boolean canPlaceShip(int row, int col, int size, boolean isVertical) {
        if (isVertical) {
            if (row + size > 10) return false;  // Out of bounds
            for (int i = 0; i < size; i++) {
                if (playerShips[row + i][col]) return false;  // Ship already placed
            }
        } else {
            if (col + size > 10) return false;  // Out of bounds
            for (int i = 0; i < size; i++) {
                if (playerShips[row][col + i]) return false;  // Ship already placed
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new BattleshipGUI();
    }
}
