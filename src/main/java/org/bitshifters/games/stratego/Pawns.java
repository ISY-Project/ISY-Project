package org.bitshifters.games.stratego;

/**
 * Enum for the Pawns in the game of Stratego
 */
public enum Pawns {
    // Regular Units
    SPY("1", "Spy"),
    SCOUT("2", "Scout"),	
    MINER("3", "Miner"),
    SERGEANT("4", "Sergeant"),
    LIEUTENANT("5", "Lieutenant"),
    CAPTAIN("6", "Captain"),
    MAJOR("7", "Major"),
    COLONEL("8", "Colonel"),
    GENERAL("9", "General"),
    MARSHAL("10", "Marshal"),

    // Special Units
    BOMB("B", "Bomb"),
    FLAG("F", "Flag"),
    

    // Special tiles
    UNKNOWN("?", "Unknown"),
    LAKE("L", "Lake"),
    WIN("W", "Win"),;

    private final String pawn;
    private final String name;
    private final String path = "src\\main\\resources\\images";

    /**
     * Constructor for the Pawns enum
     * @param pawn The Pawn String value
     * @param amount The amount of pawns
     */
    Pawns(String pawn, String name) {
        this.pawn = pawn;
        this.name = name;
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
    @Override
    public String toString() {
        return this.name + " " + this.pawn;
    }

    /**
     * Returns the string value of the pawn
     * @return the string value of the pawn
     */
    public String getPawn() {
        return pawn;
    }

    /**
     * Returns the name of the pawn
     * @return the name of the pawn
     */
    public String getName() {
        return name;
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
