package src.Telnet;

public class Move {
    String command;

    public Move(long value) {
        this.command = "move " + value;
    }

    public String get() {
        return this.command;
    }
}
