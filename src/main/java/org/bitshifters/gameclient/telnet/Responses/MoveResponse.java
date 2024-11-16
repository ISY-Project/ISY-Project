package org.bitshifters.gameclient.telnet.Responses;


public enum MoveResponse {
    PLONS("Plons"),
    BOEM("Boem"),
    TICKTACKTOE("TicTacToe"),
    GEZONKEN("Gezonken"),
    ;

    private final String text;

    @Override
    public String toString() {
        return text;
    }

    MoveResponse(String string) {
        this.text = string;
    }

    public String get() {
        return this.text;
    }
}
