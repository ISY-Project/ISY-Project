package org.bitshifters.telnet;


import org.bitshifters.games.components.GameClient;
import org.bitshifters.enums.GameTypes;
import org.bitshifters.logging.BsLogger;
import org.bitshifters.telnet.Events.Challenge;
import org.bitshifters.telnet.Events.Error;
import org.bitshifters.telnet.Events.Game;
import org.bitshifters.telnet.Events.Help;
import org.bitshifters.telnet.Events.Server;
import org.bitshifters.telnet.Exceptions.TypeMismatchException;

public class ResponseHandler {
    private static final BsLogger logger = new BsLogger(ResponseHandler.class);
    private final EventHandler eventHandler;
    private final GameClient gameClient;

    public ResponseHandler(final EventHandler eventHandler, final GameClient gameClient) throws TypeMismatchException {
        this.eventHandler = eventHandler;
        this.gameClient = gameClient;
        if (!this.IsMatchingGameType()) {
            final String errorMsg = "Game type mismatch, " + this.eventHandler.getGameType() + " != " + this.gameClient.getGameType();
            TypeMismatchException typeMismatchException = new TypeMismatchException(errorMsg);
            logger.error(typeMismatchException);
            throw typeMismatchException;
        }
    }

    private boolean IsMatchingGameType() {
        logger.debug("Matching game type: " + this.eventHandler.getGameType() + " == " + this.gameClient.getGameType());
        return this.eventHandler.getGameType() == this.gameClient.getGameType();
    }

    public boolean handle(final String response) {
        logger.info("Handling response: " + response);
        if (!this.eventHandler.isValidGameType(determineGameType(response))) {
            return false;
        }
        final String[] responseArray = response.split(" ");
        if (response.contains(Help.MESSAGE)) {
            handleHelpEvent(response);
        }
        else if (response.contains(Server.MESSAGE)) {
            handleServerEvent(response, responseArray);
        } else if (response.contains(Error.MESSAGE)) {
            handleErrorEvent(response);
        }
        return true;
    }

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
        if (currentGameType == eventHandler.getGameType()) {
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

    private void handleErrorEvent(final String response) {
        logger.info("Handling error event: " + response);
        this.eventHandler.onError(response);
    }

    private void handleHelpEvent(final String response) {
        logger.info("Handling help event: " + response);
        this.eventHandler.onHelp(response);
    }

    private void handleServerEvent(final String response, final String[] responseArray) {
        logger.info("Handling server event: " + response);
        if (response.contains(Game.MESSAGE)) {
            handleGameEvent(response, responseArray);
        }
    }

    private void handleGameEvent(final String response, final String[] responseArray) {
        logger.info("Handling game event: " + response);
        if (response.contains(Challenge.MESSAGE)) {
            handleChallengeEvent(response, responseArray);
        }
        else if (response.contains(Game.MESSAGE + "MATCH")) {
            handleMatchEvent(response);
        }
        else if (response.contains(Game.MESSAGE + "YOURTURN")) {
            handleYourTurnEvent(responseArray);
        }
        else if (response.contains(Game.MESSAGE + "MOVE")) {
            handleMoveEvent(response);
        }
        else if (response.contains(Game.MESSAGE + "WIN")) {
            handleWinEvent();
        }
        else if (response.contains(Game.MESSAGE + "LOSS")) {
            handleLossEvent();
        }
        else if (response.contains(Game.MESSAGE + "DRAW")) {
            handleDrawEvent();
        }
    }

    private void handleDrawEvent() {
        logger.info("Handling draw event");
        this.eventHandler.onDraw();
    }

    private void handleLossEvent() {
        logger.info("Handling loss event");
        this.eventHandler.onLose();
    }

    private void handleWinEvent() {
        logger.info("Handling win event");
        this.eventHandler.onWin();
    }

    private void handleMoveEvent(final String response) {
        logger.info("Handling move event: " + response);
        final String[] data = parseMove(response);
        this.eventHandler.onMove(data);
    }

    private void handleYourTurnEvent(final String[] responseArray) {
        logger.info("Handling your turn event: " + responseArray[2]);
        this.eventHandler.onYourTurn(responseArray[2]);
    }

    private void handleMatchEvent(final String response) {
        logger.info("Handling match event: " + response);
        this.eventHandler.onMatch();
    }

    private void handleChallengeEvent(final String response, final String[] responseArray) {
        logger.info("Handling challenge event: " + response);
        final String playerName = response.split(" ")[1];
        final int gameNumber = Integer.parseInt(responseArray[2]);
        final int gameName = Integer.parseInt(responseArray[3]);
        this.eventHandler.onChallenge(playerName, gameName, gameNumber);
    }

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
}