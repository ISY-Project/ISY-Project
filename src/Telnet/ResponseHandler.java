package src.Telnet;

import src.BattleshipHandler;
import src.TTTHandler;
import src.Telnet.Responses.ChallengeEvent;
import src.Telnet.Responses.GameEvent;
import src.Telnet.Responses.MoveResponse;
import src.Telnet.Responses.ServerEvent;

public class ResponseHandler {
    // read the server response, and parse the response as a event
    private final TelnetClient client;
    private final TTTHandler tttHandler;
    private final BattleshipHandler BattleshipHandler;
    
    public ResponseHandler(TelnetClient client, TTTHandler tttHandler, BattleshipHandler battleshipHandler) {
        this.client = client;
        this.tttHandler = tttHandler;
        this.BattleshipHandler = battleshipHandler;
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
        if (response.contains("Battleship")) {
            if (response.contains(ServerEvent.HELP)) {
                this.BattleshipHandler.onHelp(response);
            }
            else if (response.contains(ServerEvent.MESSAGE)) {
                if (response.contains(GameEvent.MESSAGE)) {
                    if (response.contains(ChallengeEvent.MESSAGE)) {
                        String playerName = response.split(" ")[1];
                        int gameNumber = Integer.parseInt(responseArray[2]);
                        int gameName = Integer.parseInt(responseArray[3]);
                        this.BattleshipHandler.onChallenge(playerName, gameName, gameNumber);
                    }
                    else if (response.contains(GameEvent.MESSAGE + "MATCH")) {
                        if (response.contains("Battleship")) {
                            this.BattleshipHandler.onMatch(Subscribe.BATTLESHIP);
                        } else if (response.contains("Tic-tac-toe")) {
                            this.BattleshipHandler.onMatch(Subscribe.TICTACTOE);
                        }
                    }
                    else if (response.contains(GameEvent.MESSAGE + "YOURTURN")) {
                        this.BattleshipHandler.onYourTurn(responseArray[2]);
                    }
                    else if (response.contains(GameEvent.MESSAGE + "MOVE")) {
                        long time = System.currentTimeMillis();
                        String[] data = parseMove(response);
                        long time2 = System.currentTimeMillis();
                        System.out.println("Time to parse: " + (time2 - time));
                        System.out.println("Player: " + data[0] + " Move: " + data[1] + " Result: " + data[2]);
                        this.BattleshipHandler.onMove(data[0], data[1], MoveResponse.valueOf(data[2]));
                    }
                    else if (response.contains(GameEvent.MESSAGE + "WIN")) {
                        this.BattleshipHandler.onWin();
                    }
                    else if (response.contains(GameEvent.MESSAGE + "LOSS")) {
                        this.BattleshipHandler.onLose();
                    }
                    else if (response.contains(GameEvent.MESSAGE + "DRAW")) {
                        this.BattleshipHandler.onDraw();
                    }
                }
            } else if (response.contains(ServerEvent.ERROR)) {
                this.BattleshipHandler.onError(response);
            }
        } else { // Let the TTTHandler handle the other responses
            if (response.contains(ServerEvent.HELP)) {
                this.tttHandler.onHelp(response);
            }
            else if (response.contains(ServerEvent.MESSAGE)) {
                if (response.contains(GameEvent.MESSAGE)) {
                    if (response.contains(ChallengeEvent.MESSAGE)) {
                        String playerName = response.split(" ")[1];
                        int gameNumber = Integer.parseInt(responseArray[2]);
                        int gameName = Integer.parseInt(responseArray[3]);
                        this.tttHandler.onChallenge(playerName, gameName, gameNumber);
                    }
                    else if (response.contains(GameEvent.MESSAGE + "MATCH")) {
                        if (response.contains("Battleship")) {
                            this.tttHandler.onMatch(Subscribe.BATTLESHIP);
                        } else if (response.contains("Tic-tac-toe")) {
                            this.tttHandler.onMatch(Subscribe.TICTACTOE);
                        }
                    }
                    else if (response.contains(GameEvent.MESSAGE + "YOURTURN")) {
                        this.tttHandler.onYourTurn(responseArray[2]);
                    }
                    else if (response.contains(GameEvent.MESSAGE + "MOVE")) {
                        long time = System.currentTimeMillis();
                        String[] data = parseMove(response);
                        long time2 = System.currentTimeMillis();
                        System.out.println("Time to parse: " + (time2 - time));
                        System.out.println("Player: " + data[0] + " Move: " + data[1] + " Result: " + data[2]);
                        this.tttHandler.onMove(data[0], data[1], MoveResponse.valueOf(data[2]));
                    }
                    else if (response.contains(GameEvent.MESSAGE + "WIN")) {
                        this.tttHandler.onWin();
                    }
                    else if (response.contains(GameEvent.MESSAGE + "LOSS")) {
                        this.tttHandler.onLose();
                    }
                    else if (response.contains(GameEvent.MESSAGE + "DRAW")) {
                        this.tttHandler.onDraw();
                    }
                }
            } else if (response.contains(ServerEvent.ERROR)) {
                this.tttHandler.onError(response);
            }
        }

    }

    private String[] parseMove(String response) {
        char data_start = '{';
        char data_end = '}';
        int start = response.indexOf(data_start);
        int end = response.indexOf(data_end);
        String[] data = response.substring(start + 1, end).split(",");
        String[] result = new String[3];
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