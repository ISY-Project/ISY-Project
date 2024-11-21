package org.bitshifters.games.battleships;

public class Ship {
    private final int row;
    private final int col;
    private final int length;
    private final boolean horizontal;
    private final boolean[] hits;

    public Ship(final int length) {
        this(0, 0, length, true);
    }

    public Ship(final int row, final int col, final int length, final boolean horizontal) {
        this.row = row;
        this.col = col;
        this.length = length;
        this.horizontal = horizontal;
        this.hits = new boolean[length];
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
            return (
                col >= this.col
                && col <= this.col + this.length
                && row == this.row
            );
        } else {
            return (
                col == this.col
                && row >= this.row
                && row < this.row + length
            );
        }
    }

    public boolean isHit(final int row, final int col) {
        if (isAt(row, col)) {
            return true;
        }
        return false;
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) {
                return false;
            }
        }
        return true;
    }

    public void hit(final int row, final int col) {
        hits[horizontal ? col - this.col : row - this.row] = true;
    }
}
