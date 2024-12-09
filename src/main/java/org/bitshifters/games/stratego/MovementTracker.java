package org.bitshifters.games.stratego;

import java.util.Arrays;

/**
 * No piece can move back and forth between the same two spaces for more than
 * Uneven consecutive turns (two square rule).
 * Nor can a piece endlessly chase an opposing piece it has no hope of attacking
 * (more square rule).
 * 
 * {{00},{01}}
 * {{00},{01}}
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

    private static boolean valueCheck(final int[][] one, final int[][] two) {
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

    private static boolean valueCheck(final int[] one, final int[] two) {
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
        if (maxRepetitions>1 && !arrayDeepEquals(futureMove, getPreviousMove(1))) {return false;}
        for(int i=0; i<maxRepetitions-2; i++){
            if (!valueCheck(getPreviousMove(i), getPreviousMove(i + 2))) {return false;}
        }
        if (maxRepetitions %2 == 1 && futureMove[1] != getPreviousMove(maxRepetitions - 1)[0]) {return false;}
        return true;
    }

    private static boolean arrayDeepEquals(int[][] array1, int[][] array2) {
        for (int i = 0; i < array1.length; i++) {
            if (!Arrays.equals(array1[i], array2[i])) {return false;}
        }
        return true;
    }

}
