package src.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
    private Ships ships;
    private PlayerGrid playerGrid;
    private OpponentGrid opponentGrid;
    private TickTackToeGrid tickTackToeGrid;

    public InfoPanel(Ships ships, PlayerGrid playerGrid, OpponentGrid opponentGrid) {
        super(new GridLayout(3, 0));
        this.ships = ships;
        this.playerGrid = playerGrid;
        this.opponentGrid = opponentGrid;
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        JButton resetButton = new JButton("Reset Ships");
        JButton rotateButton = new JButton("Rotate Ship");
        resetButton.addActionListener(resetShipsActionListener());
        rotateButton.addActionListener(RotateShipActionListener());

        this.add(resetButton);
        this.add(rotateButton);
        this.add(new JLabel("Select a ship to place on the grid:"));
        this.add(new JLabel("Available Ships:" + Arrays.toString(ships.ShipSizes())));
        // TODO: add information about turns. (Current, limit, player's turn etc.)
    }

    public InfoPanel(TickTackToeGrid tickTackToeGrid) {
        super(new GridLayout(4, 0));
        this.tickTackToeGrid = tickTackToeGrid;
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(100, 100));
        resetButton.addActionListener(resetTickTackToeListener());

        this.add(resetButton);
    }

    @SuppressWarnings("unused")
    private ActionListener resetTickTackToeListener() {
        return (ActionEvent e) -> {
            for (var row : tickTackToeGrid.getGrid()) {
                for (var cell : row) {
                    cell.setText("");
                    cell.setForeground(Color.black);
                }
            }
        };
    }


    @SuppressWarnings("unused")
    private ActionListener RotateShipActionListener() {
        return (ActionEvent e) -> {
            for (var ship : ships.getShips()) {
                ship.rotate();
            }
        };
    }

    
    @SuppressWarnings("unused")
    private ActionListener resetShipsActionListener() {
        return (ActionEvent e) -> {
            for (var row : playerGrid.getGrid()) {
                for (var cell : row) {
                    cell.setBackground(Color.BLUE);
                }
            }
            for (var row : opponentGrid.getGrid()) {
                for (var cell : row) {
                    cell.setBackground(Color.BLUE);
                }
            }
        };
    }

}