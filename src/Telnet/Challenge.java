package src.Telnet;

public class Challenge {
    private class Accept{
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

    public String Accept(int gameNumber) {
        Accept accept = new Accept(gameNumber);
        return accept.get();
    }

    public String get() {
        return this.command;
    }
}
