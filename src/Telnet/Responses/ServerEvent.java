package Telnet.Responses;

public interface ServerEvent {
    String message = "SVR ";
    String help = message + "HELP ";
    void onHelp(String message);
}
