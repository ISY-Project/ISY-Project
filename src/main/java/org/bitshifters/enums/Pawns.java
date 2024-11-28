package org.bitshifters.enums;

public enum Pawns {
    // Regular Units
    SPY("1"),
    SCOUT("2"),
    MINER("3"),
    SERGEANT("4"),
    LIEUTENANT("5"),
    CAPTAIN("6"),
    MAJOR("7"),
    COLONEL("8"),
    GENERAL("9"),
    MARSHAL("10"),

    // Special Units
    FLAG("F"),
    BOMB("B"),
    

    // Special tiles
    UNKNOWN("?"),
    NONE(" "),
    LAKE("L"),
    WIN("W");

    private final String pawn;
    private final String path = "src\\main\\resources\\images";

    /**
     * Constructor for the Pawns enum
     * @param pawn The Pawn String value
     * @param amount The amount of pawns
     */
    Pawns(String pawn) {
        this.pawn = pawn;
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
