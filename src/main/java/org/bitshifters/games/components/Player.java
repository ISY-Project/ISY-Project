package org.bitshifters.games.components;

import org.bitshifters.logging.BSLogger;

/**
 * Represents a player in the game.
 */
public class Player {
    private static final BSLogger logger = new BSLogger(Player.class);
    private String name;
    private int score;

    /**
     * Constructor for a player
     * @param name the name of the player
     */
    public Player(final String name) {
        logger.debug("Creating player with name: " + name);
        this.name = name;
        this.score = 0;
    }

    /**
     * Get the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the player
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the score of the player
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score of the player
     * @param score the score of the player
     */
    public void incrementScore(int score) {
        logger.debug("Incrementing score for player: " + name);
        this.score += score;
    }

    /**
     * Increment the score of the player by 1
     */
    public void incrementScore() {
        incrementScore(1);
    }
}
