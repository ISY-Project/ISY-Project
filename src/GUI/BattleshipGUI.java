package GUI;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class BattleshipGUI extends JFrame {
    private final static int GRID_SIZE = 8;
    private JButton[][] playerGrid = new JButton[GRID_SIZE][GRID_SIZE];
    private JButton[][] opponentGrid = new JButton[GRID_SIZE][GRID_SIZE];
    private boolean[][] playerShips = new boolean[GRID_SIZE][GRID_SIZE];  // To track player ships
    private Ships ships;  // Ships available to be placed
    private JPanel playerPanel;
    private JPanel opponentPanel;
    private JPanel infoPanel;
    private boolean isVertical = true;  // Ship orientation (vertical/horizontal)
    private int selectedShipSize = 0;  // Track the size of the ship being dragged
    private int[] initialShipSizes = {2, 3, 3, 4, 5};  // Ship sizes available to be placed

    public BattleshipGUI() {
        this.ships = new Ships(initialShipSizes);

        setTitle("Battleship Game");
        setSize(950, 500);
        setLayout(new BorderLayout());

        // Player's grid panel
        this.playerPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        this.playerPanel.setBorder(BorderFactory.createTitledBorder("Your Grid"));
        initializePlayerGrid(this.playerPanel);

        // Opponent's grid panel
        this.opponentPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        this.opponentPanel.setBorder(BorderFactory.createTitledBorder("Opponent Grid"));
        initializeOpponentGrid(this.opponentPanel);

        // information panel
        this.infoPanel = new JPanel(new GridLayout(20, 0));
        this.infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        initializeInfoPanel(this.infoPanel);

        // Add components to the frame
        add(this.playerPanel, BorderLayout.WEST);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.opponentPanel, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Initialize player grid with buttons
    private void initializePlayerGrid(JPanel playerPanel) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton cell = new JButton();
                cell.setBackground(Color.BLUE); // Water color
                final int finalRow = row;
                final int finalCol = col;

                cell.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // When a player clicks on their grid to place a ship
                        String chosenShip = JOptionPane.showInputDialog("What size ship do you want to place?\nAvalable sizes:" + Arrays.toString(ships.getShipSizes()));
                        if (chosenShip == null) {
                        } else if ("".equals(chosenShip)) {

                        } else if (Arrays.asList(ships.getShipSizes()).contains(Integer.valueOf(chosenShip))) {
                            selectedShipSize = Integer.parseInt(chosenShip);
                            placeShipOnGrid(finalRow, finalCol);
                        }
                        // TODO - Send ship placement message to the server
                    }
                });

                playerGrid[row][col] = cell;
                playerPanel.add(cell);
            }
        }
    }

    // Initialize opponent grid (simplified)
    private void initializeOpponentGrid(JPanel opponentPanel) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton cell = new JButton();
                cell.setBackground(Color.BLUE); // Water color
                cell.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // When a player clicks on their grid to place a ship
                        cell.setBackground(Color.GRAY); // Hit color
                        // TODO - Send shot message to the server and wait for response
                    }
                });

                opponentPanel.add(cell);
                opponentGrid[row][col] = cell;
            }
        }
    }

    private void initializeInfoPanel(JPanel infoPanel) {
        // Add buttons to the info panel

        JButton resetButton = new JButton("Reset Ships");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO - add an option to reset the ships
            }
        });

        infoPanel.add(resetButton);
        infoPanel.add(new JLabel("Select a ship to place on the grid:"));
        infoPanel.add(new JLabel("Avable Ships:" + Arrays.toString(ships.shipSizes)));
    }

    // Rotate the currently selected ship
    private void rotateShip() {
        isVertical = !isVertical;  // Toggle orientation
        if (selectedShip != null) {
            String orientation = isVertical ? "Vertical" : "Horizontal";
            JOptionPane.showMessageDialog(this, "Rotated " + selectedShip.getName() + " to " + orientation);
        }
    }

    // Place the selected ship on the player's grid
    private void placeShipOnGrid(int row, int col) {
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
            if (row + size > GRID_SIZE) return false;  // Out of bounds
            for (int i = 0; i < size; i++) {
                if (playerShips[row + i][col]) return false;  // Ship already placed
            }
        } else {
            if (col + size > GRID_SIZE) return false;  // Out of bounds
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
