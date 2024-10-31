package Telnet;

public class Challenge {
    private class Accept{
        private String command;
        private String acceptedString = "accept ";

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

    public String Accept(int gameNumber) {
        Accept accept = new Accept(gameNumber);
        return accept.get();
    }

    public String get() {
        return this.command;
    }
}
