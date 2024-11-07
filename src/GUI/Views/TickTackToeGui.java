package src.GUI.Views;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import src.GUI.Components.ChatBox;
import src.GUI.Grids.TickTackToeGrid;


public class TickTackToeGui extends JPanel {
    private final TickTackToeGrid tickTackToeGrid;
    private final InfoPanel TickTackToeInfoPanel;
    private final ChatBox chatBox;

    public TickTackToeGrid getTickTackToeGrid() {
        return tickTackToeGrid;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public InfoPanel getInformationPanel() {
        return TickTackToeInfoPanel;
    }

    public TickTackToeGui(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        this.tickTackToeGrid = new TickTackToeGrid(mainFrame);
        this.TickTackToeInfoPanel = new InfoPanel(this.tickTackToeGrid);
        this.chatBox = new ChatBox();

        // Add components to the frame
        add(this.tickTackToeGrid, BorderLayout.CENTER);
        add(this.TickTackToeInfoPanel, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);
    }
}
