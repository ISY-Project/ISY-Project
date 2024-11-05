package src.Telnet.Responses;

import src.Telnet.Subscribe;

public interface GameEvent {
    String MESSAGE = "GAME ";
    void onMatch();
    void onMatch(Subscribe game);
    void onYourTurn(String MESSAGE);
    void onMove(String player, String move, MoveResponse result);
    void onWin();
    void onLose();
    void onDraw();
}
