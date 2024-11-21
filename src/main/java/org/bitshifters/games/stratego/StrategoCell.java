package org.bitshifters.games.stratego;

public enum StrategoCell {
    // Special cells
    Empty(0), Bomb(0), Flag(0), Win(0),
    // Units
    Spy(1), Scout(2), Miner(3), Sergeant(4), Lieutenant(5),
    Captain(6), Major(7), Colonel(8), General(9), Marshal(10)
    ;


    private final int rank;

    private StrategoCell(int rank) {
        this.rank = rank;
    }

    public int getInt() {
        return rank;
    }
}
