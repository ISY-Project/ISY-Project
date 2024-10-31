package GUI;

import java.awt.*;
import javax.swing.*;

public final class MainFrame extends JFrame {
    private final StartScreen startScreen = new StartScreen(this);
    private final BattleshipGUI battleshipGUI = new BattleshipGUI(this);
    private final TickTackToe tickTackToe = new TickTackToe(this);

    public MainFrame() {
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

    public BattleshipGUI getBattleshipGUI() {
        return battleshipGUI;
    }

    public TickTackToe getTickTackToe() {
        return tickTackToe;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
