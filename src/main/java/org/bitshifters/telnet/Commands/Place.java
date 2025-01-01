package org.bitshifters.telnet.Commands;

public class Place implements SendableCommand {
    private final String command;

    /**
     * Constructor for Place command for a battleship ship
     * @param start_index
     * @param end_index
     */
    public Place(int start_index, int end_index) {
        this.command = "place " + start_index + " " + end_index;
    }

    /**
     * Constructor for Place command for the stratego unit placement
     * @param rank
     * @param index
     */
    public Place(String rank, int index) {
        this.command = "place " + index + " " + rank;
    }

    @Override
    public String get() {
        return this.command;
    }
}
