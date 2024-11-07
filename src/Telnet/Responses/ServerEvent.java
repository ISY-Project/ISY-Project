package src.Telnet.Responses;

public interface ServerEvent {
    String ID = "SVR ";
    String HELP = ID + "HELP ";
    String ERROR = "ERR ";
    void onHelp(String MESSAGE);
    void onMessage(String MESSAGE);
}
