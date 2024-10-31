package GUI;

import java.awt.*;
import javax.swing.*;

public final class MainFrame extends JFrame {
    private StartScreen startScreen;
    private BattleshipGUI battleshipGUI;
    private TickTackToe tickTackToe;

    public MainFrame() {
        startScreen = new StartScreen(this);
        battleshipGUI = new BattleshipGUI(this);
        tickTackToe = new TickTackToe(this);

        // Set layout and add panels
        setLayout(new CardLayout());
        add(startScreen, "startScreen");
        add(battleshipGUI, "battleshipGUI");
        add(tickTackToe, "tickTackToe");

        // Show Screen 1 initially
        showScreen("startScreen");

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  
    }

    public void showScreen(String screenName) {
        System.out.println(screenName);
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
