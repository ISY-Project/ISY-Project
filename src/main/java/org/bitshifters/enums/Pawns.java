package org.bitshifters.enums;

public enum Pawns {
    // Regular Units
    SPY("1", 1),
    SCOUT("2", 8),
    MINER("3", 5),
    SERGEANT("4", 4),
    LIEUTENANT("5", 4),
    CAPTAIN("6", 4),
    MAJOR("7", 3),
    COLONEL("8", 2),
    GENERAL("9", 1),
    MARSHAL("10", 1),

    // Special Units
    FLAG("F", 1),
    BOMB("B", 6),
    

    // Special tiles
    NONE(" ", 0),
    LAKE("L", 0), 
    Win("W", 0);

    private final String pawn;
    private final int amount;
    private final String path = "src\\main\\resources\\images";

    Pawns(String pawn, int amount) {
        this.pawn = pawn;
        this.amount = amount;
    }

    public String getPawn() {
        return pawn;
    }

    public int getAmount() {
        return amount;
    }

    public String getRedPath() {
        return path + "\\StrategoRed" + pawn + ".png";
    }

    public String getBluePath() {
        return path + "\\StrategoBlue" + pawn + ".png";
    }
}
