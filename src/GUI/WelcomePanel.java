package src.GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import src.Main;
import src.Telnet.Subscribe;

public class WelcomePanel extends JPanel {
        public WelcomePanel(MainFrame mainFrame) {
        super(new GridLayout(6, 0));
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        this.setSize(350, 350);
        JButton tickTackToe = new JButton("Tick Tack Toe");
        JButton battleships = new JButton("Battleships");

        tickTackToe.addActionListener(tickTackToeActionListener(mainFrame));
        battleships.addActionListener(battleshipsActionListener(mainFrame));

        tickTackToe.setPreferredSize(new Dimension(300, 75));
        battleships.setPreferredSize(new Dimension(300, 75));
        JLabel labelOne = new JLabel("Chose a game to play!");
        labelOne.setFont(new Font("Arial", Font.PLAIN, 24));
        JLabel labelTwo = new JLabel("Available games:");
        labelTwo.setFont(new Font("Arial", Font.PLAIN, 24));
        JLabel sliderLabel = new JLabel();
        sliderLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        JSlider slider = new JSlider(0, 1, 0);
        slider.setPaintTrack(true);
		slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
        sliderLabel.setText("Manual");
        this.add(labelOne);
        this.add(labelTwo);
        this.add(tickTackToe);
        this.add(battleships);
        this.add(sliderLabel);

        AlgCheckbox algorithmToggle = new AlgCheckbox();

        this.add(algorithmToggle);

        algorithmToggle.addActionListener(actionListener -> {
            if (algorithmToggle.isSelected()) {
                mainFrame.setAlgorithmOn(true);
            } else {
                mainFrame.setAlgorithmOn(false);
            }
        });
    }

    private ActionListener tickTackToeActionListener(MainFrame mainFrame) {
        return (ActionEvent e) -> {
            mainFrame.showScreen(Screens.TTT);
            Main.getTelnetClient().sendMessage(Subscribe.TTT.get());
        };
    }

    private ActionListener battleshipsActionListener(MainFrame mainFrame) {
        return (ActionEvent e) -> {
            mainFrame.showScreen(Screens.BATTLESHIP);
            Main.getTelnetClient().sendMessage(Subscribe.BATTLESHIP.get());
        };
    }

}