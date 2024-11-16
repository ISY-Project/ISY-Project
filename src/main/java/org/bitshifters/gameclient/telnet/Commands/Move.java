package org.bitshifters.gameclient.telnet.Commands;

public class Move implements SendableCommand {
    String command;

    public Move(long value) {
        this.command = "move " + value;
    }

    public String get() {
        return this.command;
    }
}
