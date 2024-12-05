package org.bitshifters.games.components;

import org.bitshifters.logging.BSLogger;

public class Player {
    private static final BSLogger logger = new BSLogger(Player.class);
    private String name;
    private int score;

    public Player(final String name) {
        logger.debug("Creating player with name: " + name);
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int score) {
        logger.debug("Incrementing score for player: " + name);
        this.score += score;
    }

    public void incrementScore() {
        incrementScore(1);
    }
}
