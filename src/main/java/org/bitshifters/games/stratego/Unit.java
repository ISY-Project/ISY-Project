package org.bitshifters.games.stratego;


import org.bitshifters.enums.Pawns;
import org.bitshifters.games.components.Player;

public class Unit {
    private final Pawns rank;
    private Player player;

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

    @Override
    public String toString() {
        return rank.toString();
    }
}
