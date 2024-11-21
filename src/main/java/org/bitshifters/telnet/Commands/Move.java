package org.bitshifters.telnet.Commands;

public class Move implements SendableCommand {
    String command;

    public Move(long value) {
        this.command = "move " + value;
    }

    @Override
    public String get() {
        return this.command;
    }
}
