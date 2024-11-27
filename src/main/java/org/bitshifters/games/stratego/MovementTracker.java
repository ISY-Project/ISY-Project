package org.bitshifters.games.stratego;

/**
 * No piece can move back and forth between the same two spaces for more than
 * three consecutive turns (two square rule).
 * Nor can a piece endlessly chase an opposing piece it has no hope of attacking
 * (more square rule).
 */
public class MovementTracker {
    private int[][][] track = { {} };
    private int maxRepeations = 3;

    public MovementTracker() {
    }

    public void add(final Unit prev, final int row, final int col) {
        int test = 0;
        final int[][] coordinate = { { prev.getRow(), prev.getCol() }, { row, col } };
        if (track.length == maxRepeations) {
            test = 1;
        }
        final int[][][] result = new int[track.length + 1 - test][][];
        result[0] = coordinate;
        for (int i = 0; i < track.length - test; i++) {
            result[i + 1] = track[i];
        }
        this.track = result;
    }

    private int[][] getPreviousMove(final int move) {
        return track[move];
    }

    public boolean isRepeating(final Unit unit, final int row, final int col) {
        // TODO check rules
        int[][] coordinate = { { unit.getRow(), unit.getCol() }, { row, col } };
        if (track.length < maxRepeations) {
            return false;
        }
        if (
            // getPreviousMove(2)[0] == coordinate[1]
            // && getPreviousMove(0)[0] == coordinate[1]
            // && getPreviousMove(0)[1] == coordinate[0]
            // && getPreviousMove(1)[1] == getPreviousMove(0)[0]
            // && getPreviousMove(2)[1] == getPreviousMove(1)[0]
            // alternative
            coordinate == getPreviousMove(1)
            && getPreviousMove(0) == getPreviousMove(2)
            && coordinate[1] == getPreviousMove(2)[0]
        ) {
            return true;
        }
        return false;
    }
}
