package org.bitshifters.ui.enums;

public enum Pawns {
    NONE(" ", 0), // Empty space for the unknown pawns
    FLAG("F", 1),
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
    BOMB("B", 6);

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
