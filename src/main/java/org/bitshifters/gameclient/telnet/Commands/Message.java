package org.bitshifters.gameclient.telnet.Commands;

public class Message implements SendableCommand {
    private final String command;

    public Message(String message) {
        this.command = "message \"" + message + "\"";
    }

    @Override
    public String get() {
        return this.command;
    }
}
