package org.bitshifters.games.stratego;

import java.util.ArrayList;
import java.util.List;

import org.bitshifters.games.components.GridTransformer;

/**
 * No piece can move back and forth between the same two spaces for more than
 * three consecutive turns (two square rule).
 * Nor can a piece endlessly chase an opposing piece it has no hope of attacking
 * (more square rule).
 */
public class MovementTracker {
    private List<Integer> moves = new ArrayList<>();
    private int size;

    public MovementTracker(int size) {
        this.size = size;
    }

    public void add(int row, int col) {
        GridTransformer<Integer> transformer = new GridTransformer<>();
        add(transformer.toIndex(row, col, size));
    }

    public void add(int index) {
        moves.add(index);
    }

    public int remove(int index) {
        int value = moves.get(index);
        moves.remove(index);
        return value;
    }

    public boolean isRepeating() {
        return isRepeating(4);
    }

    public boolean isRepeating(int turns) {
        if (moves.size() < turns) {
            return false;
        }
        if (turns < 2) {
            throw new IllegalArgumentException("Turns must be greater than 2");
        }
        var qSize = moves.size();
        int windowSize = turns - 1;
        for (int i = qSize; i - windowSize > 0; i--) {
            for (int j = 0; j < windowSize / 2; j++) {
                if (!moves.get(i - windowSize + j).equals(moves.get(i - windowSize + j + 2))) {
                    return false;
                }
            }
        }
        return true;
    }
}
