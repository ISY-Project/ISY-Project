package org.bitshifters.telnet.Commands;

public class Forfeit implements SendableCommand {
    @Override
    public String get() {
        return "forfeit";
    }
}
