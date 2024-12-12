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
    private final int maxRepetitions;

    /**
     * Create a new MovementTracker object
     * @param maxRepetitions the maximum number of repetitions allowed
     */
    public MovementTracker(final int maxRepetitions) {
        this.maxRepetitions = maxRepetitions;
    }

    /**
     * Add a move to the tracker
     * @param fromRow the row the piece is moving from
     * @param fromCol the column the piece is moving from
     * @param toRow the row the piece is moving to
     * @param toCol the column the piece is moving to
     */
    public void add(final int fromRow, final int fromCol, final int toRow, final int toCol) {
        int test = 0;
        final int[][] coordinate = { { fromRow, fromCol }, { toRow, toCol } };
        if (track.length == maxRepetitions) {
            test = 1;
        }
        final int[][][] result = new int[track.length + 1 - test][][];
        result[0] = coordinate;
        System.arraycopy(track, 0, result, 1, track.length - test);
        this.track = result;
    }

    /**
     * Get the previous move
     * @param move the move to get
     * @return the previous move
     */
    private int[][] getPreviousMove(final int move) {
        return track[move];
    }

    /**
     * Check if the move is repeating
     * @param fromRow the row the piece is moving from
     * @param fromCol the column the piece is moving from
     * @param toRow the row the piece is moving to
     * @param toCol the column the piece is moving to
     * @return true if the move is repeating, false otherwise
     */
    public boolean isRepeating(final int fromRow, final int fromCol, final int toRow, final int toCol) {
        // TODO check rules
        int[][] futureMove = { { fromRow, fromCol }, { toRow, toCol } };
        if (track.length < maxRepetitions) {return false;}
        if (maxRepetitions>1 && !arrayDeepEquals(futureMove, getPreviousMove(1))) {return false;}
        for(int i=0; i<maxRepetitions-2; i++){
            if (!arrayDeepEquals(getPreviousMove(i), getPreviousMove(i + 2))) {return false;}
        }
        return !(maxRepetitions %2 == 1 && !Arrays.equals(futureMove[1], getPreviousMove(maxRepetitions - 1)[0]));
    }

    /**
     * Check if two arrays are equal
     * @param array1 the first array
     * @param array2 the second array
     * @return true if the arrays are equal, false otherwise
     */
    private static boolean arrayDeepEquals(int[][] array1, int[][] array2) {
        for (int i = 0; i < array1.length; i++) {
            if (!Arrays.equals(array1[i], array2[i])) {return false;}
        }
        return true;
    }

}
