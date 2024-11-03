package src.GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;


public class TickTackToeGui extends JPanel implements IsyGui {
    private final TickTackToeGrid playerGrid;
    private final InfoPanel TickTackToeInfoPanel;
    private final ChatBox chatBox;

    public TickTackToeGrid getPlayerGrid() {
        return playerGrid;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public TickTackToeGui() {
        setLayout(new BorderLayout());

        this.playerGrid = new TickTackToeGrid();
        this.TickTackToeInfoPanel = new InfoPanel(this.playerGrid);
        this.chatBox = new ChatBox();

        // Add components to the frame
        add(this.playerGrid, BorderLayout.CENTER);
        add(this.TickTackToeInfoPanel, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);
    }
}
