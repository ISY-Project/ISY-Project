package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePannel extends JPanel {
    public WelcomePannel(MainFrame mainFrame) {
        super(new GridLayout(4, 0));
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
        this.add(lableOne);
        this.add(lableTwo);
        this.add(tickTackToe);
        this.add(battleships);
    }


    private ActionListener tickTackToeActionListener(MainFrame mainFrame) {
        return (@SuppressWarnings("unused") ActionEvent e) -> {
            mainFrame.setSize(600, 600);
            mainFrame.showScreen("tickTackToe");
        };
    }

    private ActionListener battleshipsActionListener(MainFrame mainFrame) {
        return (@SuppressWarnings("unused") ActionEvent e) -> {
            mainFrame.setSize(1000, 600);
            mainFrame.showScreen("battleshipGUI");
        };
    }

}