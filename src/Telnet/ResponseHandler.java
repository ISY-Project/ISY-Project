package Telnet;

import Telnet.Responses.ChallengeEvent;
import Telnet.Responses.GameEvent;
import Telnet.Responses.MoveResponse;
import Telnet.Responses.ServerEvent;

public class ResponseHandler {
    // read the server response, and parse the response as a event
    private TelnetClient client;
    private EventHandler eventHandler;
    
    ResponseHandler(TelnetClient client, EventHandler eventHandler) {
        this.client = client;
        this.eventHandler = eventHandler;
    }

    public void handle() {
        String response = null;
        try {
            this.client.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        this.handle(response);
        }
    }

    public void handle(String response) {
        // TODO: Test
        String[] responseArray = response.split(" ");
        if (response.contains(ServerEvent.help)) {
            this.eventHandler.onHelp(response);
        }
        else if (response.contains(ServerEvent.message)) {
            if (response.contains(GameEvent.message)) {
                if (response.contains(ChallengeEvent.message)) {
                    String playerName = response.split(" ")[1];
                    int gameNumber = Integer.parseInt(responseArray[2]);
                    int gameName = Integer.parseInt(responseArray[3]);
                    this.eventHandler.onChallenge(playerName, gameName, gameNumber);
                }
                else if (response.contains(GameEvent.message + "MATCH")) {
                    this.eventHandler.onMatch();
                }
                else if (response.contains(GameEvent.message + "YOURTURN")) {
                    this.eventHandler.onYourTurn(responseArray[2]);
                }
                else if (response.contains(GameEvent.message + "MOVE")) {
                    String[] data = parseMove(response);
                    this.eventHandler.onMove(data[0], data[1], MoveResponse.valueOf(data[2]));
                }
                else if (response.contains(GameEvent.message + "WIN")) {
                    this.eventHandler.onWin();
                }
                else if (response.contains(GameEvent.message + "LOSE")) {
                    this.eventHandler.onLose();
                }
                else if (response.contains(GameEvent.message + "DRAW")) {
                    this.eventHandler.onDraw();
                }
            }
        } else if (response.contains(ServerEvent.error)) {
            this.client.showMessage(response);
        }
    }

    private String[] parseMove(String response) {
        char data_start = '{';
        char data_end = '}';
        int start = response.indexOf(data_start);
        String[] data = response.substring(start + 1).split(",");
        int end = response.indexOf(data_end);
        return data;
    }
}