package Telnet;

public class Get {
    private String command;

    Get(AllowedGet name) {
        this.command = "get " + name.label;
    }

    public String get() {
        return this.command;
    }
}
