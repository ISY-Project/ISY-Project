package org.bitshifters.gameclient.telnet.Commands;

public class Login implements SendableCommand {
    private final String command;

    public Login(String name) {
        this.command = "login " + name;
    }

    public String get() {
        return this.command;
    }
}
