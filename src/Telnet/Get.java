package Telnet;

public class Get {
    private final String command;

    public Get(AllowedGet name) {
        this.command = "get " + name.label;
    }

    public String get() {
        return this.command;
    }
}
