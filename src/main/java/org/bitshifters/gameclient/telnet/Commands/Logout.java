package org.bitshifters.gameclient.telnet.Commands;

public class Logout implements SendableCommand {
    public String get() {
        return "logout";
    }
}
