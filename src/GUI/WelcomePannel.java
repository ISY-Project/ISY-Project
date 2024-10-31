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
    @SuppressWarnings("unused")
    public WelcomePannel(MainFrame mainFrame) {
        super(new GridLayout(6, 0));
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        this.setSize(350, 350);
        JButton tickTackToe = new JButton("Tick Tack Toe");
        JButton battleships = new JButton("Battleships");

        tickTackToe.addActionListener(tickTackToeActionListener(mainFrame));
        battleships.addActionListener(battleshipsActionListener(mainFrame));

        tickTackToe.setPreferredSize(new Dimension(300, 75));
        battleships.setPreferredSize(new Dimension(300, 75));
        JLabel lableOne = new JLabel("Chose a game to play!");
        lableOne.setFont(new Font("Arial", Font.PLAIN, 24));
        JLabel lableTwo = new JLabel("Available games:");
        lableTwo.setFont(new Font("Arial", Font.PLAIN, 24));
        JLabel sliderLabel = new JLabel();
        sliderLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        JSlider slider = new JSlider(0, 1, 0);
        slider.setPaintTrack(true);
		slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
        sliderLabel.setText("Manual");
        mainFrame.setAlgorithmOn(false);
        slider.addChangeListener((e) -> {
            String text = slider.getValue() == 0 ? "Manual" : "Algorithm";
            sliderLabel.setText(text);
            mainFrame.setAlgorithmOn(slider.getValue() == 1);
        });
        this.add(lableOne);
        this.add(lableTwo);
        this.add(tickTackToe);
        this.add(battleships);
        this.add(sliderLabel);
        this.add(slider);
    }

    private ActionListener tickTackToeActionListener(MainFrame mainFrame) {
        return (@SuppressWarnings("unused") ActionEvent e) -> {
            mainFrame.setSize(600, 600);
            mainFrame.showScreen("tickTackToe");
            mainFrame.selectGame(Subscribe.TICTACTOE);
        };
    }

    private ActionListener battleshipsActionListener(MainFrame mainFrame) {
        return (@SuppressWarnings("unused") ActionEvent e) -> {
            mainFrame.setSize(1000, 600);
            mainFrame.showScreen("battleshipGUI");
            mainFrame.selectGame(Subscribe.BATTLESHIP);
        };
    }

}