package src.GUI.Grids;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class BaseGrid extends JPanel {
    BaseGrid(int gridSize) {
        super(new GridLayout(gridSize, gridSize));
    }

    protected JButton[][] grid;
    abstract JButton[][] getGrid();
    abstract void resetGrid();
    abstract void enableGrid();
    abstract void disableGrid();
    abstract protected void initializeGrid(int gridSize);
}
