package src.Telnet;

import src.GameType;
import src.Telnet.Responses.ChallengeEvent;
import src.Telnet.Responses.GameEvent;
import src.Telnet.Responses.ServerEvent;

public class ResponseHandler {
    private final EventHandler eventHandler;
    
    public ResponseHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public boolean handle(String response) {
        if (!this.eventHandler.isValidGameType(determineGameType(response))) {
            return false;
        }
        String[] responseArray = response.split(" ");
        if (response.contains(ServerEvent.HELP)) {
            handleHelpEvent(response);
        }
        else if (response.contains(ServerEvent.ID)) {
            handleServerEvent(response, responseArray);
        } else if (response.contains(ServerEvent.ERROR)) {
            handleErrorEvent(response);
        }
        return true;
    }

    private GameType determineGameType(String response) {
        char data_start = '{';
        char data_end = '}';
        int start = response.indexOf(data_start);
        int end = response.indexOf(data_end);
        if (start == -1 || end == -1) {
            return null;
        }
        String[] data = response.substring(start + 1, end).split(",");
        GameType result = null;
        for (String option : data) {
            if (option.contains("Tic-tac-toe")) {
                result = GameType.TTT;
            }
            if (option.contains("Battleship")) {
                result = GameType.BATTLESHIP;
            }
        }
        return result;
    }

    private void handleErrorEvent(String response) {
        this.eventHandler.onError(response);
    }

    private void handleHelpEvent(String response) {
        this.eventHandler.onHelp(response);
    }

    private void handleServerEvent(String response, String[] responseArray) {
        if (response.contains(GameEvent.MESSAGE)) {
            handleGameEvent(response, responseArray);
        }
    }

    private void handleGameEvent(String response, String[] responseArray) {
        if (response.contains(ChallengeEvent.MESSAGE)) {
            handleChallengeEvent(response, responseArray);
        }
        else if (response.contains(GameEvent.MESSAGE + "MATCH")) {
            handleMatchEvent(response);
        }
        else if (response.contains(GameEvent.MESSAGE + "YOURTURN")) {
            handleYourTurnEvent(responseArray);
        }
        else if (response.contains(GameEvent.MESSAGE + "MOVE")) {
            handleMoveEvent(response);
        }
        else if (response.contains(GameEvent.MESSAGE + "WIN")) {
            handleWinEvent();
        }
        else if (response.contains(GameEvent.MESSAGE + "LOSS")) {
            handleLossEvent();
        }
        else if (response.contains(GameEvent.MESSAGE + "DRAW")) {
            handleDrawEvent();
        }
    }

    private void handleDrawEvent() {
        this.eventHandler.onDraw();
    }

    private void handleLossEvent() {
        this.eventHandler.onLose();
    }

    private void handleWinEvent() {
        this.eventHandler.onWin();
    }

    private void handleMoveEvent(String response) {
        String[] data = parseMove(response);
        this.eventHandler.onMove(data);
    }

    private void handleYourTurnEvent(String[] responseArray) {
        this.eventHandler.onYourTurn(responseArray[2]);
    }

    private void handleMatchEvent(String response) {
        this.eventHandler.onMatch();
    }

    private void handleChallengeEvent(String response, String[] responseArray) {
        String playerName = response.split(" ")[1];
        int gameNumber = Integer.parseInt(responseArray[2]);
        int gameName = Integer.parseInt(responseArray[3]);
        this.eventHandler.onChallenge(playerName, gameName, gameNumber);
    }

    private String[] parseMove(String response) {
        char data_start = '{';
        char data_end = '}';
        int start = response.indexOf(data_start);
        int end = response.indexOf(data_end);
        String[] data = response.substring(start + 1, end).split(",");
        String[] result = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            String option = data[i].trim();
            char split = '"';
            if (option.contains(String.valueOf(split) + String.valueOf(split))) {
                option = "TICKTACKTOE";
            }
            if (option.contains(String.valueOf(split))) {
                option = option.split(String.valueOf(split))[1];
            }
            result[i] = option;
        }
        return result;
    }
}