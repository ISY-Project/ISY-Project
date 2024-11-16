package org.bitshifters.games;

public class Player {
    private String name;
    private int score;

    public Player(final String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
}
