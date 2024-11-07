package src.GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.GameType;
import src.Main;
import src.Telnet.Subscribe;

public class InfoPanel extends JPanel {
    private Ships ships;
    private TickTackToeGrid tickTackToeGrid;
    private final JLabel turnLabel = new JLabel("Wait on opponent");

    public InfoPanel(Ships ships, PlayerGrid playerGrid, OpponentGrid opponentGrid) {
        super(new GridLayout(3, 0));
        this.ships = ships;
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

    public void setYourTurnLabel(String text) {
        this.turnLabel.setText(text);
    }

    private void forfeit() {
        if (Main.getGameType() != GameType.NONE || Main.getGameType() != GameType.TTT) {
            Main.getTelnetClient().sendMessage("forfeit");
        }
    }

    private ActionListener resetTickTackToeListener() {
        return (ActionEvent e) -> {
            tickTackToeGrid.clearGrid();
            forfeit();
            Main.getMainFrame().getTickTackToe().getChatBox().clearChat();
            setYourTurnLabel("Wait on opponent");
            Main.getTelnetClient().sendMessage(Subscribe.TTT.get());
            Main.setGameType(GameType.NONE);
            Main.toggleTTTGrid();
        };
    }

    private ActionListener backBattleListener() {
        return (ActionEvent e) -> {
            forfeit();
            Main.getMainFrame().getBattleshipGUI().getChatBox().clearChat();
            Main.getMainFrame().showScreen(Screens.START_SCREEN);
            setYourTurnLabel("Not in game");
            Main.setGameType(GameType.NONE);
            Main.toggleBattleshipGrid();
        };
    }

    private ActionListener backTicListener() {
        return (ActionEvent e) -> {
            tickTackToeGrid.clearGrid();
            forfeit();
            Main.getMainFrame().showScreen(Screens.START_SCREEN);
            Main.setGameType(GameType.NONE);
            setYourTurnLabel("Not in game");
            Main.toggleTTTGrid();
        };
    }


    private ActionListener RotateShipActionListener() {
        return (ActionEvent e) -> {
            for (var ship : ships.getShips()) {
                ship.rotate();
            }
        };
    }

    
    private ActionListener resetShipsActionListener() {
        return (ActionEvent e) -> {
            forfeit();
            Main.setGameType(GameType.NONE);
            Main.getTelnetClient().sendMessage(Subscribe.BATTLESHIP.get());
            Main.toggleBattleshipGrid();
        };
    }
}