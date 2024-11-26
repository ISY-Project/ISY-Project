package org.bitshifters.games.stratego;


import org.bitshifters.enums.Pawns;
import org.bitshifters.games.components.Player;

public class Unit {
    private final Pawns rank;
    private Player player;
    private int row;
    private int col;

    public Unit(Pawns rank) {
        this(rank, null);
    }

    public Unit(Pawns rank, Player player) {
        this(rank, player, -1, -1);
    }

    public Unit(Pawns rank, int row, int col) {
        this(rank, null, row, col);
    }

    public Unit(Pawns rank, Player player, int row, int col) {
        this.rank = rank;
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public boolean isPlaced() {
        return row != -1 && col != -1;
    }

    public Pawns getRank() {
        return rank;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        setCoordinate(this.col, row);
    }

    public void setCol(int col) {
        setCoordinate(col, this.row);
    }

    public void setCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void remove() {
        this.row = -1;
        this.col = -1;
    }

    public Unit asRotated(int totalRows, int totalCols) {
        totalRows--;
        totalCols--;
        return new Unit(rank, player, totalRows - row, totalCols - col);
    }

    @Override
    public String toString() {
        return rank + " at (" + row + ", " + col + ")";
    }
}
