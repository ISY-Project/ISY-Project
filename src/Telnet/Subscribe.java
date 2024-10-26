package Telnet;

public class Subscribe {
    private String command;

    public Subscribe(String game) {
        // TODO: use a enum.
        this.command = "subscribe " + game;
    }

    public String get() {
        return this.command;
    }
}
