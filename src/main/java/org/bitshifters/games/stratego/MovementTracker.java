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
    private int[][] track = {};
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
     * @param from the index the piece is moving from
     * @param Col the index the piece is moving from
     */
    public void add(final int from, final int to) {
        int test = 0;
        final int[] coordinate = {from, to};
        if (track.length == maxRepetitions) {
            test = 1;
        }
        final int[][] result = new int[track.length + 1 - test][];
        result[0] = coordinate;
        for (int i = 0; i < track.length - test; i++) {
            result[i + 1] = track[i];
        }
        this.track = result;
    }

    /**
     * Get the previous move
     * @param move the move to get
     * @return the previous move
     */
    private int[] getPreviousMove(final int move) {
        return track[move];
    }

    /**
     * Check if the move is repeating
     * @param from the index the piece is moving from
     * @param to the indes the piece is moving to
     * @return true if the move is repeating, false otherwise
     */
    public boolean isRepeating(final int from, final int to) {
        // TODO check rules
        int[] futureMove = { from, to };
        if (
            track.length < maxRepetitions // If less moves have been made then the max amount of repetitions, it returns false
            || (maxRepetitions>1 && !Arrays.equals(futureMove, getPreviousMove(1))) // if 2 or more moves have been made, this checks if the next move isn't the same as, 2 moves ago
            || (maxRepetitions %2 == 1 && (futureMove[1] != getPreviousMove(maxRepetitions - 1)[0])) // if maxRepetitions is uneven, checks if the first movement doesn't end where the last movement starts
        ) {
            return false;
        } 
        // checks if every other movement isn't the same
        for(int i=0; i<maxRepetitions-2; i++){
            if (!Arrays.equals(getPreviousMove(i), getPreviousMove(i + 2))) return false;
        }
        return true;
    }

    // function for printing the movementTrack
    public void printMovementTrack() {
        System.out.print("[");
        for (int i = 0; i < track.length; i++) {
            System.out.print(Arrays.toString(track[i]));
            if (i != track.length - 1) System.out.print(", ");
        }
        System.out.println("]");
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
