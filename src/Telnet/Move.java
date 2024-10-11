package Telnet;

public class Move {
    String command;

    Move(int value) {
        this.command = "move" + value;
    }

    public String get() {
        return "logout";
    }
}
