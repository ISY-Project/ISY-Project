package org.bitshifters.gameclient.telnet.Enums;

public enum AllowedGet {
    GAMELIST("gamelist"),
    PLAYERLIST("playerlist"),
    ;

    
    public final String label;

    private AllowedGet(String label) {
        this.label = label;
    }
}
