package org.bitshifters.games.stratego;

import org.bitshifters.games.components.Player;

public class Unit {
    private final StrategoCell rank;
    private final Player player;
    private int row;
    private int col;

    public Unit(StrategoCell rank) {
        this(rank, null);
    }

    public Unit(StrategoCell rank, Player player) {
        this(rank, player, -1, -1);
    }

    public Unit(StrategoCell rank, Player player, int row, int col) {
        this.rank = rank;
        this.player = player;
        this.row = -1;
        this.col = -1;
    }

    public boolean isPlaced() {
        return row != -1 && col != -1;
    }

    public StrategoCell getRank() {
        return rank;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void remove() {
        this.row = -1;
        this.col = -1;
    }
}
