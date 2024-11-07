package src.Telnet.Commands;

public class Move {
    String command;

    public Move(long value) {
        this.command = "move " + value;
    }

    public String get() {
        return this.command;
    }
}
