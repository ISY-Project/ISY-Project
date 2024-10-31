package src.GUI;

import java.awt.CardLayout;
import javax.swing.*;
import src.Main;
import src.Telnet.Subscribe;

public final class MainFrame extends JFrame {
    private final StartScreen startScreen = new StartScreen(this);
    private final BattleshipGUI battleshipGUI = new BattleshipGUI(this);
    private final Main main;
    private final TickTackToe tickTackToe;

    public MainFrame(Main main) {
        this.main = main;
        this.tickTackToe = new TickTackToe(this, main);
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

    public void selectGame(Subscribe game) {
        this.main.selectGame(game);
        
    }

    public BattleshipGUI getBattleshipGUI() {
        return battleshipGUI;
    }

    public TickTackToe getTickTackToe() {
        return tickTackToe;
    }
}
