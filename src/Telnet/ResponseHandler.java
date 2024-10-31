package Telnet;

import Telnet.Responses.ChallengeEvent;
import Telnet.Responses.GameEvent;
import Telnet.Responses.MoveResponse;
import Telnet.Responses.ServerEvent;

public class ResponseHandler {
    // read the server response, and parse the response as a event
    private final TelnetClient client;
    private final EventHandler eventHandler;
    
    public ResponseHandler(TelnetClient client, EventHandler eventHandler) {
        this.client = client;
        this.eventHandler = eventHandler;
    }

    @SuppressWarnings("CallToPrintStackTrace")
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
            if (response.contains(GameEvent.MESSAGE)) {
                if (response.contains(ChallengeEvent.MESSAGE)) {
                    String playerName = response.split(" ")[1];
                    int gameNumber = Integer.parseInt(responseArray[2]);
                    int gameName = Integer.parseInt(responseArray[3]);
                    this.eventHandler.onChallenge(playerName, gameName, gameNumber);
                }
                else if (response.contains(GameEvent.MESSAGE + "MATCH")) {
                    this.eventHandler.onMatch();
                }
                else if (response.contains(GameEvent.MESSAGE + "YOURTURN")) {
                    this.eventHandler.onYourTurn(responseArray[2]);
                }
                else if (response.contains(GameEvent.MESSAGE + "MOVE")) {
                    String[] data = parseMove(response);
                    this.eventHandler.onMove(data[0], data[1], MoveResponse.valueOf(data[2]));
                }
                else if (response.contains(GameEvent.MESSAGE + "WIN")) {
                    this.eventHandler.onWin();
                }
                else if (response.contains(GameEvent.MESSAGE + "LOSS")) {
                    this.eventHandler.onLose();
                }
                else if (response.contains(GameEvent.MESSAGE + "DRAW")) {
                    this.eventHandler.onDraw();
                }
            }
        } else if (response.contains(ServerEvent.error)) {
            this.eventHandler.onError(response);
        }
    }

    private String[] parseMove(String response) {
        char data_start = '{';
        char data_end = '}';
        int start = response.indexOf(data_start);
        String[] data = response.substring(start + 1).split(",");
        int end = response.indexOf(data_end);
        System.out.println(end);
        return data;
    }
}