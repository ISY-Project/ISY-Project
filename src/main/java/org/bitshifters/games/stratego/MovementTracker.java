package org.bitshifters.games.stratego;

/**
 * No piece can move back and forth between the same two spaces for more than
 * Uneven consecutive turns (two square rule).
 * Nor can a piece endlessly chase an opposing piece it has no hope of attacking
 * (more square rule).
 */
public class MovementTracker {
    private int[][][] track = { {} };
    private int maxRepetitions;

    public MovementTracker(final int maxRepetitions) {
        this.maxRepetitions = maxRepetitions;
    }

    public void add(final int fromRow, final int fromCol, final int toRow, final int toCol) {
        int test = 0;
        final int[][] coordinate = { { fromRow, fromCol }, { toRow, toCol } };
        if (track.length == maxRepetitions) {
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

    private boolean valueCheck(final int[][] one, final int[][] two) {
        if (one.length != two.length) {
            return false;
        }
        for (int i = 0; i < one.length; i++) {
            if (!valueCheck(one[i], two[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean valueCheck(final int[] one, final int[] two) {
        if (one.length != two.length) {
            return false;
        }
        for (int i = 0; i < one.length; i++) {
            if (one[i] != two[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isRepeating(final int fromRow, final int fromCol, final int toRow, final int toCol) {
        // TODO check rules
        int[][] futureMove = { { fromRow, fromCol }, { toRow, toCol } };
        if (track.length < maxRepetitions) {return false;}
        if (maxRepetitions>1 && !valueCheck(futureMove, getPreviousMove(1))) {return false;}
        for(int i=0; i<maxRepetitions-2; i++){
            if (!valueCheck(getPreviousMove(i), getPreviousMove(i + 2))) {return false;}
        }
        if (maxRepetitions %2 == 1 && !valueCheck(futureMove[1], getPreviousMove(maxRepetitions - 1)[0])) {return false;}
        return true;
        // old version
        // if (
        //     futureMove == getPreviousMove(1)
        //     && getPreviousMove(0) == getPreviousMove(2)
        //     && futureMove[1] == getPreviousMove(2)[0]
        // ) {
        //     return true;
        // }
        // return false;
    }
}
