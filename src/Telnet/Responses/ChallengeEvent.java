package Telnet.Responses;

public interface ChallengeEvent {
    String message = "CHALLENGE ";
    void onChallenge(String playerName, int game, int gameNumber);
    void onCancel(int gameNumber);
}
