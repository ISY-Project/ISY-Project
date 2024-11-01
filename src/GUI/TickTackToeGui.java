package src.GUI;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import src.Main;


public class TickTackToeGui extends JPanel implements IsyGui {
    private final TickTackToeGrid playerGrid;
    private final InfoPanel TickTackToeInfoPanel;
    private final ChatBox chatBox;
    private final AlgCheckbox algorithmToggle;

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
        this.algorithmToggle = new AlgCheckbox(Main.getTTTEngine());

        // Add components to the frame
        add(this.playerGrid, BorderLayout.CENTER);
        add(this.TickTackToeInfoPanel, BorderLayout.EAST);
        add(this.chatBox, BorderLayout.SOUTH);
        add(this.algorithmToggle, BorderLayout.WEST);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
        this.algorithmToggle.addActionListener(actionListener -> {
            if (this.algorithmToggle.isSelected()) {
                disableGrid();
            } else {
                enableGrid();
            }
        });
    }

    private void enableGrid() {
        setGrid(true);
    }

    private void disableGrid() {
        setGrid(false);
    }

    private void setGrid(boolean value) {
        for (JButton[] row : this.playerGrid.getGrid()) {
            for (JButton cell : row)
                cell.setEnabled(value);
        };
    }

}
