package org.bitshifters.gameclient.telnet;

public class Place {
    private final String command;

    public Place(int start_index, int end_index) {
        this.command = "place " + start_index + " " + end_index;
    }

    public String get() {
        return this.command;
    }
}
