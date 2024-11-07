package src.Telnet.Responses;


public interface GameEvent {
    String MESSAGE = "GAME ";
    void onMatch();
    void onYourTurn(String MESSAGE);
    void onMove(String player, String move, MoveResponse result);
    void onWin();
    void onLose();
    void onDraw();
}
