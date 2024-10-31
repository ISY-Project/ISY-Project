package src.Telnet.Responses;

public interface ServerEvent {
    String MESSAGE = "SVR ";
    String HELP = MESSAGE + "HELP ";
    String ERROR = "ERR ";
    void onHelp(String MESSAGE);
    void onMessage(String MESSAGE);
}
