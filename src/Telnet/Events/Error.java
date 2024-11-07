package src.Telnet.Events;

public interface Error {
    String MESSAGE = "ERR ";
    String NOTLOGEDIN = MESSAGE + "Not logged in";

    void onError(String error);
}
