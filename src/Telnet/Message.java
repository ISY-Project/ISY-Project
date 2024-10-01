package Telnet;

public class Message {
    private String command;

    public Message(String message) {
        this.command = "message \"" + message + "\"";;
    }

    public String get() {
        return this.command;
    }
}
