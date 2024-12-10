package org.bitshifters.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class BaseGrid extends GridPane {
    private final Button[][] grid;
    private final int gridHeight;
    private final int gridWidth;

    /**
     * Create a new BaseGrid object, The grid will be a square
     * @param gridSize the size of the grid
     */
    public BaseGrid(int gridSize) {
        this.gridHeight = gridSize;
        this.gridWidth = gridSize;
        this.grid = new Button[gridSize][gridSize];
        this.setAlignment(Pos.CENTER);
        fillGrid();
    }

    /**
     * Create a new BaseGrid object, The grid will be a rectangle
     * @param gridWidth the width of the grid
     * @param gridHeight the height of the grid
     */
    public BaseGrid(int gridWidth, int gridHeight) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.grid = new Button[gridHeight][gridWidth];
        this.setAlignment(Pos.CENTER);
        fillGrid();
    }

    /**
     * Fill the grid with buttons
     */
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

    /**
     * Get the width of the grid
     * @return the width of the grid
     */
    public int getGridWidth() {	
        return this.gridWidth;
    }

    /**
     * Get the height of the grid
     * @return the height of the grid
     */
    public int getGridHeight() {
        return this.gridHeight;
    }

    /**
     * Get the grid
     * @return the grid
     */
    public Button[][] getButtonGrid() {
        return this.grid;
    }

    /**
     * Clear the text of the grid
     */
    public void clearGrid() {
        for (Button[] row : this.grid) {
            for (Button cell : row) {
                cell.setText("");
            }
        }
    }

    /**
     * Enable the grid
     */
    public void enableGrid() {
        for (Button[] row : this.grid) {
            for (Button cell : row) {
                cell.setDisable(false);
            }
        }
    }

    /**
     * Disable the grid
     */
    public void disableGrid() {
        for (Button[] row : this.grid) {
            for (Button cell : row) {
                cell.setDisable(true);
            }
        }
    }
}