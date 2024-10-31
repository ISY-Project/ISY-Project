package src.GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;

// TODO - Add a way to join an leave the game and start the game

public class TickTackToe extends JPanel implements IsyGui {
    private final TickTackToeGrid playerGrid;
    private final InfoPanel TickTackToeInfoPanel;
    private final ChatBox chatBox;
    private Boolean algorithmOn = false;
    private Boolean isPlayerTurn = true;

    public TickTackToeGrid getPlayerGrid() {
        return playerGrid;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public TickTackToe() {
        setLayout(new BorderLayout());

        this.playerGrid = new TickTackToeGrid();
        this.TickTackToeInfoPanel = new InfoPanel(this.playerGrid);
        this.chatBox = new ChatBox();

        // Add components to the frame
        add(this.playerGrid, BorderLayout.CENTER);
        add(this.TickTackToeInfoPanel, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
    }

    public boolean getAlgorithmOn() {
        return algorithmOn;
    }

    public void setAlgorithmOn(Boolean algorithmOn) {
        this.algorithmOn = algorithmOn;
    }

    public boolean getIsPlayerTurn() {
        return this.isPlayerTurn;
    }

    public void setIsPlayerTurn(Boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }
}
