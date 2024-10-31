package Telnet;

public class Move {
    String command;

    public Move(int value) {
        this.command = "move" + value;
    }

    public String get() {
        return "logout";
    }
}
