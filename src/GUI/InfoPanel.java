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
import src.Main;
import src.Telnet.Subscribe;

public class InfoPanel extends JPanel {
    private Ships ships;
    private PlayerGrid playerGrid;
    private OpponentGrid opponentGrid;
    private TickTackToeGrid tickTackToeGrid;
    private final JLabel turnLabel = new JLabel("Wait on opponent");

    public InfoPanel(Ships ships, PlayerGrid playerGrid, OpponentGrid opponentGrid) {
        super(new GridLayout(3, 0));
        this.ships = ships;
        this.playerGrid = playerGrid;
        this.opponentGrid = opponentGrid;
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        JButton resetButton = new JButton("Reset Ships");
        JButton rotateButton = new JButton("Rotate Ship");
        JButton backButton = new JButton("Back");
        resetButton.addActionListener(resetShipsActionListener());
        rotateButton.addActionListener(RotateShipActionListener());
        backButton.addActionListener(backBattleListener());

        
        this.add(new JLabel("Select a ship to place on the grid:"));
        this.add(new JLabel("Available Ships:" + Arrays.toString(ships.ShipSizes())));
        this.add(rotateButton);
        this.add(this.turnLabel);
        this.add(resetButton);
        this.add(backButton);
    }

    public InfoPanel(TickTackToeGrid tickTackToeGrid) {
        super(new GridLayout(4, 0));
        setSize(new Dimension(150, 600));
        this.tickTackToeGrid = tickTackToeGrid;
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        JButton resetButton = new JButton("Reset");
        JButton backButton = new JButton("Back");
        resetButton.setPreferredSize(new Dimension(100, 100));
        resetButton.addActionListener(resetTickTackToeListener());
        backButton.addActionListener(backTicListener());

        this.add(resetButton);
        this.add(backButton);
        this.add(this.turnLabel);
    }

    public void setYourTurnLable(String text) {
        this.turnLabel.setText(text);
    }

    @SuppressWarnings("unused")
    private ActionListener resetTickTackToeListener() {
        return (ActionEvent e) -> {
            resetTicGrid();
            Main.getClient().sendMessage("forfeit");
            Main.getMainFrame().getTickTackToe().getChatBox().clearChat();
            setYourTurnLable("Wait on opponent");
            Main.getClient().sendMessage(Subscribe.TICTACTOE.get());
            Main.setInMatch(false);
            Main.toggleTTTGrid();
        };
    }

    private void resetTicGrid() {
        for (var row : tickTackToeGrid.getGrid()) {
            for (var cell : row) {
                cell.setText("");
                cell.setForeground(Color.black);
            }
        }
    }

    @SuppressWarnings("unused")
    private ActionListener backBattleListener() {
        return (ActionEvent e) -> {
            Main.getClient().sendMessage("forfeit");
            Main.getMainFrame().getTickTackToe().getChatBox().clearChat();
            Main.getMainFrame().showScreen(Screens.START_SCREEN);
            setYourTurnLable("Wait on opponent");
            Main.setInMatch(false);
            Main.toggleBattleshipGrid();
        };
    }

    @SuppressWarnings("unused")
    private ActionListener backTicListener() {
        return (ActionEvent e) -> {
            boolean inMatch;
            resetTicGrid();
            Main.getClient().sendMessage("forfeit");
            Main.getMainFrame().showScreen(Screens.START_SCREEN);
            Main.setInMatch(false);
            Main.toggleTTTGrid();
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
            Main.getClient().sendMessage("forfeit");
            Main.setInMatch(false);
            Main.getClient().sendMessage(Subscribe.BATTLESHIP.get());
            Main.toggleBattleshipGrid();
        };
    }
}