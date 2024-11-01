package src.GUI;

import java.awt.CardLayout;
import javax.swing.*;
import src.Main;
import src.Telnet.Subscribe;

// TODO: Mainframe should be the code that handles the interaction between engine and GUI.
public final class MainFrame extends JFrame {
    private final StartScreen startScreen = new StartScreen(this);
    private final BattleshipGUI battleshipGUI = new BattleshipGUI();
    private final TickTackToeGui tickTackToe = new TickTackToeGui();
    // private final Main main;
    // private final TickTackToe tickTackToe;

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

    public void setGame(Subscribe game) {
        Main.joinGameLobby(game);
    }

    public BattleshipGUI getBattleshipGUI() {
        return battleshipGUI;
    }

    public TickTackToeGui getTickTackToe() {
        return tickTackToe;
    }
}
