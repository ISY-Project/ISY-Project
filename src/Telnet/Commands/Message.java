package src.Telnet.Commands;

public class Message {
    private final String command;

    public Message(String message) {
        this.command = "message \"" + message + "\"";
    }

    public String get() {
        return this.command;
    }
}
