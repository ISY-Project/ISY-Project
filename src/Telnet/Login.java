package Telnet;

public class Login {
    private String command;

    Login(String name) {
        this.command = "login " + name;
    }

    public String get() {
        return this.command;
    }
}
