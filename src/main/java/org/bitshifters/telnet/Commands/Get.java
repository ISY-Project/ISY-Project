package org.bitshifters.telnet.Commands;

import org.bitshifters.telnet.Enums.AllowedGet;

public class Get implements SendableCommand {
    private final String command;

    public Get(AllowedGet name) {
        this.command = "get " + name.label;
    }

    @Override
    public String get() {
        return this.command;
    }
}
