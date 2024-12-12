package org.bitshifters.games.components;

/**
 * Represents the engine for a game.
 */
public abstract class Engine {
    protected Player activePlayer;
    
    /**
     * Constructor for the engine.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Set the active player.
     * @param activePlayer the player to set as active
     */
    public void setActivePlayer(final Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Check if it is the player's turn
     * @param player the player to check
     * @return true if it is the player's turn
     */
    protected boolean isPlayerTurn(final Player player) {
        return activePlayer == player;
    }

    /**
     * Make a move in the game.
     * @param row the row to move to
     * @param col the column to move to
     * @param player the player making the move
     * @return whether the move is valid
     */
    public abstract boolean validateMove(final int row, final int col, final Player player);

    /**
     * Check if the game is over
     * @return true if the game is over
     */
    public abstract boolean isGameOver();

    /**
     * Get the winner of the game
     * @return the Player object of the winner of the game
     */
    public abstract Player getWinner();
}
