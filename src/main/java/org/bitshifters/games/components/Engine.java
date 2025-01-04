package org.bitshifters.games.components;

import org.bitshifters.gameserver.components.GameList;

/**
 * Represents the engine for a game.
 */
public abstract class Engine {
    protected Player activePlayer;
    
    public Engine() {
        GameList.addGame(this);
    }

    /**
     * Get the active player.
     * @return the active player
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
