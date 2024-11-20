package org.bitshifters.games.battleships;

public enum BattleshipCell {
    EMPTY(Double.NEGATIVE_INFINITY),
    SHIP(0), HIT(1), MISS(2);

    private final double value;

    BattleshipCell(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public boolean contains(final BattleshipCell cell) {
        return this.value % cell.value == 0;
    }
}
