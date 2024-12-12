package org.bitshifters.games.tictactoe;

/**
 * Enum for the different types of cells in TicTacToe.
 */
public enum TicTacToeCell {
    X("X"), 
    O("O"),
    EMPTY(" ");

    private final String typeChar;
    private final String path = "src\\main\\resources\\images";

    /**
     * Constructor for TicTacToeCell.
     * @param typeChar the type of cell
     */
    TicTacToeCell(String typeChar) {
        this.typeChar = typeChar;
    }

    /**
     * Get the type of cell.
     * @return the type of cell
     */
    public String getChar() {
        return typeChar;
    }

    /**
     * Get the path for the red cell.
     * @return the path for the red cell
     */
    public String getRedPath() {
        return path + "\\Red" + typeChar + ".png";
    }

    /**
     * Get the path for the blue cell.
     * @return the path for the blue cell
     */
    public String getBluePath() {
        return path + "\\Blue" + typeChar + ".png";
    }

}
