package org.bitshifters.telnet.Commands;

public class Login implements SendableCommand {
    private final String command;

    public Login(String name) {
        this.command = "login " + name;
    }

    @Override
    public String get() {
        return this.command;
    }
}
