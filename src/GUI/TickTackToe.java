package src.GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import src.Main;

// TODO - Add a way to join an leave the game and start the game

public class TickTackToe extends JPanel implements IsyGui {
    private final TickTackToeGrid tickTackToeGrid;
    private final InfoPanel TickTackToeInfoPanel;
    private final ChatBox chatBox;
    private final Main main;

    public TickTackToeGrid getTickTackToeGrid() {
        return tickTackToeGrid;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public TickTackToe(MainFrame mainFrame, Main main) {
        this.main = main;
        setLayout(new BorderLayout());

        this.tickTackToeGrid = new TickTackToeGrid(main);
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
