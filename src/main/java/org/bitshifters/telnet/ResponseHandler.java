package org.bitshifters.telnet;

import org.bitshifters.gameclient.GameClient;
import org.bitshifters.gameclient.StrategoClient;
import org.bitshifters.gameclient.StrategoClient.CombatResult;
import org.bitshifters.games.GameTypes;
import org.bitshifters.games.stratego.Pawns;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Events.AttackResult;
import org.bitshifters.telnet.Events.Challenge;
import org.bitshifters.telnet.Events.DefenseResult;
import org.bitshifters.telnet.Events.Error;
import org.bitshifters.telnet.Events.Game;
import org.bitshifters.telnet.Events.Help;
import org.bitshifters.telnet.Events.Placed;
import org.bitshifters.telnet.Events.Server;
import org.bitshifters.telnet.Exceptions.TypeMismatchException;

/**
 * Handles responses from the server.
 */
public class ResponseHandler {
    private static final BSLogger logger = new BSLogger(ResponseHandler.class);
    private final GameClient gameClient;

    /**
     * Constructs a ResponseHandler.
     * 
     * @param eventHandler The event handler.
     * @param gameClient   The game client.
     * @throws TypeMismatchException If the game types do not match.
     */
    public ResponseHandler(final GameClient gameClient) {
        this.gameClient = gameClient;
    }

    /**
     * Handles the response from the server.
     * 
     * @param response The response from the server.
     * @return True if the response was handled, false otherwise.
     */
    public boolean handle(final String response) {
        logger.info("Handling response: " + response);
        System.out.println("Response: " + response);
        if (!this.gameClient.isValidGameType(determineGameType(response))) {
            return false;
        }
        final String[] responseArray = response.split(" ");
        if (response.contains(Help.MESSAGE)) {
            handleHelpEvent(response);
        } else if (response.contains(Server.MESSAGE)) {
            handleServerEvent(response, responseArray);
        } else if (response.contains(Error.MESSAGE)) {
            handleErrorEvent(response);
        }
        return true;
    }

    /**
     * Determines the game type from the response.
     * 
     * @param response The response from the server.
     * @return The game type.
     */
    private GameTypes determineGameType(final String response) {
        logger.debug("Determining game type: " + response);
        final char data_start = '{';
        final char data_end = '}';
        final int start = response.indexOf(data_start);
        final int end = response.indexOf(data_end);
        if (start == -1 || end == -1) {
            return null;
        }
        final GameTypes currentGameType = gameClient.getGameType();
        if (currentGameType == this.gameClient.getGameType()) {
            return currentGameType;
        }
        final String[] data = response.substring(start + 1, end).split(",");
        GameTypes result = null;
        for (final String option : data) {
            if (option.contains("Tic-tac-toe")) {
                result = GameTypes.TicTacToe;
                break;
            }
            if (option.contains("Battleship")) {
                result = GameTypes.BattleShip;
                break;
            }
            if (option.contains("Stratego")) {
                result = GameTypes.Stratego;
                break;
            }
        }
        logger.debug("Determined game type: " + result);
        return result;
    }

    /**
     * Handles an error event.
     * 
     * @param response The response from the server.
     */
    private void handleErrorEvent(final String response) {
        logger.info("Handling error event: " + response);
        this.gameClient.onError(response);
    }

    /**
     * Handles a help event.
     * 
     * @param response The response from the server.
     */
    private void handleHelpEvent(final String response) {
        logger.info("Handling help event: " + response);
        this.gameClient.onHelp(response);
    }

    /**
     * Handles a server event.
     * 
     * @param response      The response from the server.
     * @param responseArray The response array.
     */
    private void handleServerEvent(final String response, final String[] responseArray) {
        logger.info("Handling server event: " + response);
        if (response.contains(Game.MESSAGE)) {
            handleGameEvent(response, responseArray);
        }
    }

    /**
     * Handles a game event.
     * 
     * @param response      The response from the server.
     * @param responseArray The response array.
     */
    private void handleGameEvent(final String response, final String[] responseArray) {
        logger.info("Handling game event: " + response);
        if (response.contains(Challenge.MESSAGE)) {
            handleChallengeEvent(response, responseArray);
        } else if (response.contains(Game.MESSAGE + "MATCH")) {
            handleMatchEvent(response);
        } else if (response.contains(Game.MESSAGE + "YOURTURN")) {
            handleYourTurnEvent(responseArray);
        } else if (response.contains(Game.MESSAGE + "MOVE")) {
            handleMoveEvent(response);
        } else if (response.contains(Game.MESSAGE + "WIN")) {
            handleWinEvent();
        } else if (response.contains(Game.MESSAGE + "LOSS")) {
            handleLossEvent();
        } else if (response.contains(Game.MESSAGE + "DRAW")) {
            handleDrawEvent();
        } else if (response.contains(Placed.MESSAGE)) {
            handlePlacedEvent(response);
        } else if (response.contains(AttackResult.MESSAGE)) {
            handleAttackResultEvent(response);
        } else if (response.contains(DefenseResult.MESSAGE)) {
            handleDefendResultEvent(response);
        }
    }

    /**
     * Handles a draw event.
     */
    private void handleDrawEvent() {
        logger.info("Handling draw event");
        this.gameClient.onDraw();
    }

    /**
     * Handles a loss event.
     */
    private void handleLossEvent() {
        logger.info("Handling loss event");
        this.gameClient.onLose();
    }

    /**
     * Handles a win event.
     */
    private void handleWinEvent() {
        logger.info("Handling win event");
        this.gameClient.onWin();
    }

    /**
     * Handles a move event.
     * 
     * @param response The response from the server.
     */
    private void handleMoveEvent(final String response) {
        logger.info("Handling move event: " + response);
        final String[] data = parseMove(response);
        this.gameClient.onMove(data);
    }

    /**
     * Parses a move from the response.
     * 
     * @param response The response from the server.
     * @return The parsed move data.
     */
    private String[] parseCombatResult(final String response) {
        logger.debug("Parsing combat: " + response);
        final char data_start = '{';
        final char data_end = '}';
        final int start = response.indexOf(data_start);
        final int end = response.indexOf(data_end);
        final String[] data = response.substring(start + 1, end).split(",");
        final String[] result = new String[data.length];

        // TODO: Edit to parse combat data.
        for (int i = 0; i < data.length; i++) {
            String option = data[i].trim();
            final char split = ':';
            final String key = option.split(String.valueOf(split))[0];
            final String value = option.split(String.valueOf(split))[1];
            // Sanitize the value
            result[i] = value.replaceAll("[^a-zA-Z]", "");
        }

        logger.debug("Parsed move: " + result);
        return result;
    }

    private void handleAttackResultEvent(final String response) {
        logger.info("Handling attack result event: " + response);
        final String[] data = parseCombatResult(response);
        Pawns defender = Pawns.valueOf(data[0]);
        CombatResult result = CombatResult.valueOf(data[1]);
        StrategoClient SC = (StrategoClient) this.gameClient;
        SC.onAttackResult(defender, result);
    }

    private void handleDefendResultEvent(final String response) {
        logger.info("Handling defend result event: " + response);
        final String[] data = parseCombatResult(response);
        Pawns attacker = Pawns.valueOf(data[0]);
        CombatResult result = CombatResult.valueOf(data[1]);
        StrategoClient SC = (StrategoClient) this.gameClient;
        SC.onDefenseResult(attacker,result);
    }

    /**
     * Handles a "your turn" event.
     * 
     * @param responseArray The response array.
     */
    private void handleYourTurnEvent(final String[] responseArray) {
        logger.info("Handling your turn event: " + responseArray[2]);
        this.gameClient.onYourTurn(responseArray[2]);
    }

    /**
     * Handles a match event.
     * 
     * @param response The response from the server.
     */
    private void handleMatchEvent(final String response) {
        logger.info("Handling match event: " + response);
        this.gameClient.onMatch();
    }

    /**
     * Handles a challenge event.
     * 
     * @param response      The response from the server.
     * @param responseArray The response array.
     */
    private void handleChallengeEvent(final String response, final String[] responseArray) {
        logger.info("Handling challenge event: " + response);
        final String playerName = response.split(" ")[1];
        final int gameNumber = Integer.parseInt(responseArray[2]);
        final int gameName = Integer.parseInt(responseArray[3]);
        this.gameClient.onChallenge(playerName, gameName, gameNumber);
    }

    private void handlePlacedEvent(final String response) {
        logger.info("Handling placed event: " + response);
        final int placed = parsePlaced(response);
        this.gameClient.onPlaced(placed);
    }

    /**
     * Parses a move from the response.
     * 
     * @param response The response from the server.
     * @return The parsed move data.
     */
    private String[] parseMove(final String response) {
        logger.debug("Parsing move: " + response);
        final char data_start = '{';
        final char data_end = '}';
        final int start = response.indexOf(data_start);
        final int end = response.indexOf(data_end);
        final String[] data = response.substring(start + 1, end).split(",");
        final String[] result = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            String option = data[i].trim();
            final char split = '"';
            if (option.contains(String.valueOf(split) + String.valueOf(split))) {
                option = "TICKTACKTOE";
            }
            if (option.contains(String.valueOf(split))) {
                option = option.split(String.valueOf(split))[1];
            }
            result[i] = option;
        }
        logger.debug("Parsed move: " + result);
        return result;
    }

    private int parsePlaced(final String Response) {
        logger.debug("Parsing placed: " + Response);
        final String sanitized = Response.replaceAll("[^0-9]", "");
        final int result = Integer.parseInt(sanitized);
        logger.debug("Parsed placed: " + result);
        return result;
    }
}