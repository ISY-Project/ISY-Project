package src.GUI;

import java.awt.Color;
import javax.swing.JButton;

public class GridCell extends JButton{
    public GridCell() {
        super();
        this.setBackground(Color.BLUE); // Water color
    }

    public void setColor(Color color) {
        this.setBackground(color);
    }
}
