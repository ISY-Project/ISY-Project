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
import src.Telnet.Subscribe;

public class WelcomePannel extends JPanel {
    public WelcomePannel(GameEngine engine) {
        super(new GridLayout(6, 0));
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        this.setSize(350, 350);
        JButton tickTackToe = new JButton("Tick Tack Toe");
        JButton battleships = new JButton("Battleships");

        tickTackToe.addActionListener(tickTackToeActionListener(engine));
        battleships.addActionListener(battleshipsActionListener(engine));

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
        engine.setAlgorithmOn(false);
        slider.addChangeListener((e) -> {
            String text = slider.getValue() == 0 ? "Manual" : "Algorithm";
            sliderLabel.setText(text);
            engine.setAlgorithmOn(slider.getValue() == 1);
        });
        this.add(labelOne);
        this.add(labelTwo);
        this.add(tickTackToe);
        this.add(battleships);
        this.add(sliderLabel);
        this.add(slider);
    }

    private ActionListener tickTackToeActionListener(GameEngine engine) {
        return (ActionEvent e) -> {
            engine.setSize(600, 600);
            engine.showScreen("tickTackToe");
            engine.setGame(Subscribe.TICTACTOE);
        };
    }

    private ActionListener battleshipsActionListener(GameEngine engine) {
        return (ActionEvent e) -> {
            engine.setSize(1000, 600);
            engine.showScreen("battleshipGUI");
            engine.setGame(Subscribe.BATTLESHIP);
        };
    }

}