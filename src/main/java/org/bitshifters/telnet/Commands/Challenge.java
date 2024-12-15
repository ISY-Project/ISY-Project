package org.bitshifters.telnet.Commands;

/**
 * Represents a challenge command.
 */
public class Challenge implements SendableCommand {
    private class Accept implements SendableCommand {
        private final String command;
        private final String acceptedString = "accept ";

        /**
         * Constructs an Accept command.
         * @param gameNumber The game number to accept.
         */
        private Accept(int gameNumber) {
            this.command = challengeString + acceptedString + gameNumber;
        }

        /**
         * Gets the accept command string.
         * @return The accept command string.
         */
        @Override
        public String get() {
            return this.command;
        }
    }
    String command;
    private final String challengeString = "challenge ";

    /**
     * Constructs a Challenge command.
     * @param playerName The name of the player to challenge.
     * @param game The game to challenge.
     */
    public Challenge(String playerName, int game) {
        this.command = challengeString + playerName + " " + game;
    }

    /**
     * Creates an Accept command for the challenge.
     * @param gameNumber The game number to accept.
     * @return The Accept command.
     */
    public Accept Accept(int gameNumber) {
        Accept accept = new Accept(gameNumber);
        return accept;
    }

    /**
     * Gets the challenge command string.
     * @return The challenge command string.
     */
    @Override
    public String get() {
        return this.command;
    }
}
