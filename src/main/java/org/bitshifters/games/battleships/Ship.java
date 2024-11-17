package org.bitshifters.games.battleships;

public class Ship {
    private final int row;
    private final int col;
    private final int length;
    private final boolean horizontal;
    private int hits = 0;

    public Ship(final int row, final int col, final int length, final boolean horizontal) {
        this.row = row;
        this.col = col;
        this.length = length;
        this.horizontal = horizontal;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getLength() {
        return length;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return !horizontal;
    }

    public boolean isAt(final int row, final int col) {
        if (horizontal) {
            return col == this.col && row >= this.row && row < this.row + length;
        } else {
            return row == this.row && col >= this.col && col < this.col + length;
        }
    }

    public boolean isHit(final int row, final int col) {
        if (isAt(row, col)) {
            return true;
        }
        return false;
    }

    public boolean isSunk() {
        return hits == length;
    }

    public void hit() {
        hits++;
    }
}
