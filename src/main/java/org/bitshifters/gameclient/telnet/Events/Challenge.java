package org.bitshifters.gameclient.telnet.Events;

import org.bitshifters.gameclient.telnet.Commands.SendableCommand;

public class Challenge {
    private class Accept implements SendableCommand {
        private final String command;
        private final String acceptedString = "accept ";

        private Accept(int gameNumber) {
            this.command = challengeString + acceptedString + gameNumber;
        }

        public String get() {
            return this.command;
        }
    }
    String command;
    private final String challengeString = "challenge ";

    public Challenge(String playerName, int game) {
        this.command = challengeString + playerName + " " + game;
    }

    public Accept Accept(int gameNumber) {
        Accept accept = new Accept(gameNumber);
        return accept;
    }

    public String get() {
        return this.command;
    }
}
