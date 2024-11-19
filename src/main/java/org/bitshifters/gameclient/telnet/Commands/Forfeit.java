package org.bitshifters.gameclient.telnet.Commands;

public class Forfeit implements SendableCommand {
    @Override
    public String get() {
        return "forfeit";
    }
}
