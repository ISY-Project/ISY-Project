package src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.GameEngine.Engine;
import src.Telnet.Subscribe;
import src.Main;

public class WelcomePanel extends JPanel {
    private final MainFrame mainframe;
    private final AlgCheckbox algorithmToggle;

    public WelcomePanel(MainFrame mainframe) {
        super(new GridLayout(6, 0));
        this.mainframe = mainframe;
        this.algorithmToggle = new AlgCheckbox(null, "Use Algorithm");

        this.setBorder(BorderFactory.createTitledBorder("Information"));
        this.setSize(350, 350);

        JButton tickTackToe = new JButton("Tick Tack Toe");
        JButton battleships = new JButton("Battleships");

        tickTackToe.addActionListener(tickTackToeActionListener(mainframe));
        battleships.addActionListener(battleshipsActionListener(mainframe));

        tickTackToe.setPreferredSize(new Dimension(300, 75));
        battleships.setPreferredSize(new Dimension(300, 75));

        JLabel labelOne = new JLabel("Choose a game to play!");
        labelOne.setFont(new Font("Arial", Font.PLAIN, 24));
        JLabel labelTwo = new JLabel("Available games:");
        labelTwo.setFont(new Font("Arial", Font.PLAIN, 24));

        this.add(labelOne);
        this.add(labelTwo);
        this.add(tickTackToe);
        this.add(battleships);
        this.add(algorithmToggle);
    }

    private ActionListener tickTackToeActionListener(MainFrame mainframe) {
        return (ActionEvent e) -> {
            algorithmToggle.setEngine(Main.getTTTEngine());
            algorithmToggle.doClick();
            mainframe.setSize(600, 600);
            mainframe.showScreen("tickTackToe");
            mainframe.setGame(Subscribe.TICTACTOE);
            Main.getClient().sendMessage(Subscribe.TICTACTOE.get());
            new Thread(() -> Main.runTicTacToeComp()).start();
        };
    }

    private ActionListener battleshipsActionListener(MainFrame mainframe) {
        return (ActionEvent e) -> {
            algorithmToggle.setEngine(Main.getBattleshipEngine());
            algorithmToggle.doClick();
            mainframe.setSize(1000, 600);
            mainframe.showScreen("battleshipGUI");
            mainframe.setGame(Subscribe.BATTLESHIP);
            Main.getClient().sendMessage(Subscribe.BATTLESHIP.get());
            new Thread(() -> Main.runTicTacToeComp()).start();
        };
    }
}