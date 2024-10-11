package Telnet.Responses;

public interface GameEvent {
    String message = "GAME ";
    void onMatch();
    void onYourTurn(String message);
    void onMove(String player, String move, MoveResponse result);
    public ChallengeEvent challengeHandler = null;
    void onWin();
    void onLose();
    void onDraw();
}
