package Telnet;

public interface Error {
    String message = "ERR ";
    String notLoggedIn = message + "Not logged in";

    void onError(String error);
}
