package src.GUI;

import java.awt.Dimension;
import java.awt.Font;
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

@SuppressWarnings("unused")
public class InfoPanel extends JPanel {
    private Ships ships;
    private TickTackToeGrid tickTackToeGrid;
    private final JLabel turnLabel = new JLabel("Wait on opponent");
    private final JButton yourChar = new JButton(" ");

    public InfoPanel(Ships ships) {
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
        this.tickTackToeGrid = tickTackToeGrid;
        JButton resetButton = new JButton("Reset");
        JButton backButton = new JButton("Back");
        resetButton.addActionListener(resetTickTackToeListener());
        backButton.addActionListener(backTicListener());
        this.yourChar.setEnabled(false);
        this.yourChar.setFont(new Font("Arial", Font.BOLD, 60));
        this.yourChar.setFocusPainted(false);

        this.add(resetButton);
        this.add(backButton);
        this.add(this.yourChar);
        this.add(this.turnLabel);
    }

    public void setPlayerChar(String playerChar) {
        this.yourChar.setText(playerChar);
    }

    public void setYourTurnLabel(String text) {
        this.turnLabel.setText(text);
    }

    private void forfeit() {
        if (Main.getGameType() != GameType.NONE) {
            Main.getTelnetClient().sendMessage("forfeit");
            Main.setGameType(GameType.NONE);
        }
    }

    private ActionListener resetTickTackToeListener() {
        return (ActionEvent e) -> {
            tickTackToeGrid.clearGrid();
            forfeit();
            tickTackToeGrid.setIsPlayerX(false);
            tickTackToeGrid.setFirstMove(true);
            Main.getMainFrame().getTickTackToe().getChatBox().clearChat();
            setYourTurnLabel("Wait on opponent");
            setPlayerChar(" ");
            Main.toggleTTTGrid();
            Main.getTelnetClient().sendMessage(Subscribe.TTT.get());
        };
    }

    private ActionListener backBattleListener() {
        return (ActionEvent e) -> {
            forfeit();
            Main.getMainFrame().getBattleshipGUI().getChatBox().clearChat();
            Main.getMainFrame().showScreen(Screens.START_SCREEN);
            Main.toggleBattleshipGrid();
            setYourTurnLabel("Wait on opponent");
        };
    }

    private ActionListener backTicListener() {
        return (ActionEvent e) -> {
            tickTackToeGrid.clearGrid();
            forfeit();
            tickTackToeGrid.setIsPlayerX(false);
            tickTackToeGrid.setFirstMove(true);
            Main.getMainFrame().getTickTackToe().getChatBox().clearChat();
            Main.getMainFrame().showScreen(Screens.START_SCREEN);
            Main.toggleTTTGrid();
            setYourTurnLabel("Wait on opponent");
            setPlayerChar(" ");
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
            Main.getTelnetClient().sendMessage(Subscribe.BATTLESHIP.get());
            Main.toggleBattleshipGrid();
        };
    }
}