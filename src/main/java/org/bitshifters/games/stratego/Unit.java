package org.bitshifters.games.stratego;

import org.bitshifters.games.components.Player;

/**
 * Represents a unit in the game of Stratego.
 */
public class Unit {
    private final Pawns rank;
    private Player player;

    /**
     * Constructor for a unit
     * @param rank the rank of the unit
     */
    public Unit(Pawns rank) {
        this(rank, null);
    }

    /**
     * Constructor for a unit
     * @param rank the rank of the unit
     * @param player the player that the unit belongs to
     */
    public Unit(Pawns rank, Player player) {
        this(rank, player, -1, -1);
    }

    /**
     * Constructor for a unit
     * @param rank the rank of the unit
     * @param row the row of the unit
     * @param col the column of the unit
     */
    public Unit(Pawns rank, int row, int col) {
        this(rank, null, row, col);
    }

    /**
     * Constructor for a unit
     * @param rank the rank of the unit
     * @param player the player that the unit belongs to
     * @param row the row of the unit
     * @param col the column of the unit
     */
    public Unit(Pawns rank, Player player, int row, int col) {
        this.rank = rank;
        this.player = player;
    }

    /**
     * Get the rank of the unit
     * @return the rank of the unit
     */
    public Pawns getRank() {
        return rank;
    }

    /**
     * Get the player that the unit belongs to
     * @return the player that the unit belongs to
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player that the unit belongs to
     * @param player the player that the unit belongs to
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the string representation of the unit
     * @return the string representation of the unit
     */
    @Override
    public String toString() {
        return rank.toString();
    }
}
