package src.GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;

// TODO - Add a way to join an leave the game and start the game

public class TickTackToe extends JPanel {
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

    public TickTackToe(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        this.tickTackToeGrid = new TickTackToeGrid(mainFrame);
        this.TickTackToeInfoPanel = new InfoPanel(this.tickTackToeGrid);
        this.chatBox = new ChatBox();

        // Add components to the frame
        add(this.tickTackToeGrid, BorderLayout.CENTER);
        add(this.TickTackToeInfoPanel, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
    }
}
