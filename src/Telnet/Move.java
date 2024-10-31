package src.Telnet;

public class Move {
    String command;

    public Move(int value) {
        this.command = "move " + value;
    }

    public String get() {
        return this.command;
    }
}
