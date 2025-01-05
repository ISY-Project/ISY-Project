package org.bitshifters.telnet.Commands;

public class Move implements SendableCommand {
    String command;

    public Move(long value) {
        this.command = "move " + value;
    }

    public Move(int from, int to) {
        this.command = "move " + from + " " + to;
    }

    @Override
    public String get() {
        return this.command;
    }
}
