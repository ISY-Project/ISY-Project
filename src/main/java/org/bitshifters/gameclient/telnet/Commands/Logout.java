package org.bitshifters.gameclient.telnet.Commands;

public class Logout implements SendableCommand {
    @Override
    public String get() {
        return "logout";
    }
}
