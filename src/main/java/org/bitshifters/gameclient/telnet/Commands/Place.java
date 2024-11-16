package org.bitshifters.gameclient.telnet.Commands;

public class Place implements SendableCommand {
    private final String command;

    public Place(int start_index, int end_index) {
        this.command = "place " + start_index + " " + end_index;
    }

    public String get() {
        return this.command;
    }
}
