package org.bitshifters.games.battleships;

public enum BattleshipCell {
    EMPTY(Double.NEGATIVE_INFINITY),
    SHIP(0), HIT(1), MISS(2);

    private double value;

    BattleshipCell(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void remove(final BattleshipCell cell) throws IllegalArgumentException {
        if (!contains(cell)) {
            throw new IllegalArgumentException("Cannot remove a cell that is not contained in this cell.");
        }
        this.value -= cell.value;
    }

    public void add(final BattleshipCell cell) {
        if (contains(cell)) {
            throw new IllegalArgumentException("Cannot add a cell that is already contained in this cell.");
        }
        this.value += cell.value;
    }

    public boolean contains(final BattleshipCell cell) {
        return this.value % cell.value == 0;
    }
}
