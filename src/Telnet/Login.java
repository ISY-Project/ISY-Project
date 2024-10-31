package Telnet;

public class Login {
    private final String command;

    public Login(String name) {
        this.command = "login " + name;
    }

    public String get() {
        return this.command;
    }
}
