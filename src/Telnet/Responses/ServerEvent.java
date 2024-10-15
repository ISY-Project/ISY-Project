package Telnet.Responses;

public interface ServerEvent {
    String message = "SVR ";
    String help = message + "HELP ";
    String error = "ERR ";
    void onHelp(String message);
}
