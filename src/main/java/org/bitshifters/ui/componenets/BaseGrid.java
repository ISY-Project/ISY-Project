package org.bitshifters.ui.componenets;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class BaseGrid extends GridPane {
    private Button[][] grid;
    private final int gridHeight;
    private final int gridWidth;

    public BaseGrid(int gridSize) {
        this.gridHeight = gridSize;
        this.gridWidth = gridSize;
        this.grid = new Button[gridSize][gridSize];
        this.setAlignment(Pos.CENTER);
        fillGrid();
    }

    public BaseGrid(int gridWidth, int gridHeight) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.grid = new Button[gridHeight][gridWidth];
        this.setAlignment(Pos.CENTER);
        fillGrid();
    }

    private void fillGrid() {
        for (int row = 0; row < this.gridHeight; row++) {
            for (int col = 0; col < this.gridWidth; col++) {
                Button cell = new Button();
                cell.setFont(new Font("Arial", 20));
                cell.setFocusTraversable(false);
                this.add(cell, col, row);
                this.grid[row][col] = cell;
            }
        }
    }

    public int getGridWidth() {	
        return this.gridWidth;
    }

    public int getGridHeight() {
        return this.gridHeight;
    }

    public Button[][] getButtonGrid() {
        return this.grid;
    }

    public void clearGrid() {
        for (Button[] row : this.grid) {
            for (Button cell : row) {
                cell.setText("");
            }
        }
    }

    public void enableGrid() {
        for (Button[] row : this.grid) {
            for (Button cell : row) {
                cell.setDisable(false);
            }
        }
    }

    public void disableGrid() {
        for (Button[] row : this.grid) {
            for (Button cell : row) {
                cell.setDisable(true);
            }
        }
    }
}