package Telnet;

import Telnet.Responses.ChallengeEvent;
import Telnet.Responses.GameEvent;
import Telnet.Responses.MoveResponse;
import Telnet.Responses.ServerEvent;

public class ResponseHandler {
    // read the server response, and parse the response as a event
    private TelnetClient client;
    private ServerEvent eventHandler;
    
    ResponseHandler(TelnetClient client, ServerEvent eventHandler) {
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
                var gameHandler = this.eventHandler.gameHandler;
                if (response.contains(ChallengeEvent.message)) {
                    var challengeHandler = gameHandler.challengeHandler;
                    String playerName = response.split(" ")[1];
                    int gameNumber = Integer.parseInt(responseArray[2]);
                    int gameName = Integer.parseInt(responseArray[3]);
                    challengeHandler.onChallenge(playerName, gameName, gameNumber);
                }
                if (response.contains(GameEvent.message + "MATCH")) {
                    gameHandler.onMatch();
                }
                if (response.contains(GameEvent.message + "YOURTURN")) {
                    gameHandler.onYourTurn(responseArray[2]);
                }
                if (response.contains(GameEvent.message + "MOVE")) {
                    char data_start = '{';
                    char data_end = '}';
                    int start = response.indexOf(data_start);
                    String[] data = response.substring(start + 1).split(",");
                    int end = response.indexOf(data_end);
                    gameHandler.onMove(data[0], data[1], MoveResponse.valueOf(data[2]));
                }
                if (response.contains(GameEvent.message + "WIN")) {
                    gameHandler.onWin();
                }
                if (response.contains(GameEvent.message + "LOSE")) {
                    gameHandler.onLose();
                }
                if (response.contains(GameEvent.message + "DRAW")) {
                    gameHandler.onDraw();
                }
            }
        }
    }
}