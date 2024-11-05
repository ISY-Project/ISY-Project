package src.GUI;

import java.awt.CardLayout;
import javax.swing.*;

public final class MainFrame extends JFrame {
    private final StartScreen startScreen = new StartScreen(this);
    private final BattleshipGUI battleshipGUI = new BattleshipGUI(this);
    private final TickTackToe tickTackToe = new TickTackToe(this);
    private Boolean algorithmOn = false;
    private Boolean isPlayerTurn = false;
    private static final JOptionPane jOptionPane = new JOptionPane();

    public MainFrame() {
        // Set layout and add panels
        setLayout(new CardLayout());
        add(startScreen, Screens.START_SCREEN.get());
        add(battleshipGUI, Screens.BATTLESHIP_GUI.get());
        add(tickTackToe, Screens.TICK_TACK_TOE.get());

        // Show Screen 1 initially
        showScreen(Screens.START_SCREEN);

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  
        
        add(jOptionPane);
    }

    public JOptionPane getOptionPane() {
        return jOptionPane;
    }

    public void showScreen(Screens screen) {
        switch (screen) {
            case START_SCREEN -> {
                setSize(500, 500);
            }
            case BATTLESHIP_GUI -> {
                setSize(1000, 600);
            }
            case TICK_TACK_TOE -> {
                setSize(600, 600);
            }
            default -> throw new AssertionError();
        }
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), screen.get());
    }

    public BattleshipGUI getBattleshipGUI() {
        return battleshipGUI;
    }

    public TickTackToe getTickTackToe() {
        return tickTackToe;
    }

    public Boolean getAlgorithmOn() {
        return algorithmOn;
    }

    public void setAlgorithmOn(Boolean algorithmOn) {
        this.algorithmOn = algorithmOn;
    }

    public Boolean getIsPlayerTurn() {
        return this.isPlayerTurn;
    }

    public void setIsPlayerTurn(Boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }
}
