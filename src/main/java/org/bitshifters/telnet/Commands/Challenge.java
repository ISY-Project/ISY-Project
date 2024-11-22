package org.bitshifters.telnet.Commands;

public class Challenge implements SendableCommand {
    private class Accept implements SendableCommand {
        private final String command;
        private final String acceptedString = "accept ";

        private Accept(int gameNumber) {
            this.command = challengeString + acceptedString + gameNumber;
        }

        @Override
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

    @Override
    public String get() {
        return this.command;
    }
}
