package org.bitshifters.games.tictactoe;

public enum TicTacToeCell {
    X("X"), 
    O("O"),
    EMPTY(" ");

    private final String typeChar;
    private final String path = "src\\main\\resources\\images";

    TicTacToeCell(String typeChar) {
        this.typeChar = typeChar;
    }

    public String getChar() {
        return typeChar;
    }

    public String getRedPath() {
        return path + "\\Red" + typeChar + ".png";
    }

    public String getBluePath() {
        return path + "\\Blue" + typeChar + ".png";
    }

}
