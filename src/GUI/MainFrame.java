package src.GUI;

import java.awt.CardLayout;
import javax.swing.*;

public final class MainFrame extends JFrame {
    private Boolean algorithmOn = false;
    private Boolean isPlayerTurn = false;
    private final StartScreen startScreen = new StartScreen(this);
    private final BattleshipGUI battleshipGUI = new BattleshipGUI(this);
    private final TickTackToe tickTackToe = new TickTackToe(this);
    private static final JOptionPane jOptionPane = new JOptionPane();

    public MainFrame() {
        // Set layout and add panels
        setLayout(new CardLayout());
        add(startScreen, Screens.START_SCREEN.get());
        add(battleshipGUI, Screens.BATTLESHIP.get());
        add(tickTackToe, Screens.TTT.get());

        // Show Screen 1 initially
        showScreen(Screens.START_SCREEN);

        setSize(500, 600);
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
                setSize(500, 600);
            }
            case BATTLESHIP -> {
                setSize(1280, 720);
            }
            case TTT -> {
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
