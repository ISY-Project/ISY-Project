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
    UNKNOWN("?", 0),
    NONE(" ", 0),
    LAKE("L", 0), 
    WIN("W", 0);

    private final String pawn;
    private final int amount;
    private final String path = "src\\main\\resources\\images";

    /**
     * Constructor for the Pawns enum
     * @param pawn The Pawn String value
     * @param amount The amount of pawns
     */
    Pawns(String pawn, int amount) {
        this.pawn = pawn;
        this.amount = amount;
    }

    /**
     * Returns the integer value of the pawn
     * if the pawn is a special pawn, return 0
     * @return the integer value of the pawn
     */
    public int getInt() {
        if (pawn.equals("F") || pawn.equals("B") || pawn.equals("L") || pawn.equals("W") || pawn.equals(" ")) {
            return 0;
        }
        return Integer.parseInt(pawn);
    }

    /**
     * Returns the string value of the pawn
     * @return the string value of the pawn
     */
    public String getPawn() {
        return pawn;
    }

    /**
     * Returns the amount of pawns
     * @return the amount of pawns
     */
    public int getAmount() {
        return amount;
    }

    /** 
     * Returns the path of the red pawn
     * @return the path of the red pawn
     */
    public String getRedPath() {
        return path + "\\StrategoRed" + pawn + ".png";
    }

    /**
     * Returns the path of the blue pawn
     * @return the path of the blue pawn
     */
    public String getBluePath() {
        return path + "\\StrategoBlue" + pawn + ".png";
    }
}
