package org.bitshifters.gameclient.telnet;

import org.bitshifters.GameClient;
import org.bitshifters.gameclient.games.GameTypes;
import org.bitshifters.gameclient.telnet.Events.Challenge;
import org.bitshifters.gameclient.telnet.Events.Game;
import org.bitshifters.gameclient.telnet.Events.Help;
import org.bitshifters.gameclient.telnet.Events.Server;
import org.bitshifters.gameclient.telnet.Exceptions.TypeMismatchException;
import org.bitshifters.gameclient.telnet.Events.Error;

public class ResponseHandler {
    private final EventHandler eventHandler;
    private final GameClient gameClient;

    public ResponseHandler(final EventHandler eventHandler, final GameClient gameClient) throws TypeMismatchException {
        this.eventHandler = eventHandler;
        this.gameClient = gameClient;
        if (!this.IsMatchingGameType())
            throw new TypeMismatchException("Game type mismatch, " + this.eventHandler.getGameType() + " != " + this.gameClient.getGameType());
    }

    private boolean IsMatchingGameType() {
        return this.eventHandler.getGameType() == this.gameClient.getGameType();
    }

    public boolean handle(final String response) {
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
        final char data_start = '{';
        final char data_end = '}';
        final int start = response.indexOf(data_start);
        final int end = response.indexOf(data_end);
        if (start == -1 || end == -1) {
            return null;
        }
        final GameTypes currentGameType = gameClient.getGameType(); // use the game client.
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
        return result;
    }

    private void handleErrorEvent(final String response) {
        this.eventHandler.onError(response);
    }

    private void handleHelpEvent(final String response) {
        this.eventHandler.onHelp(response);
    }

    private void handleServerEvent(final String response, final String[] responseArray) {
        if (response.contains(Game.MESSAGE)) {
            handleGameEvent(response, responseArray);
        }
    }

    private void handleGameEvent(final String response, final String[] responseArray) {
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
        this.eventHandler.onDraw();
    }

    private void handleLossEvent() {
        this.eventHandler.onLose();
    }

    private void handleWinEvent() {
        this.eventHandler.onWin();
    }

    private void handleMoveEvent(final String response) {
        final String[] data = parseMove(response);
        this.eventHandler.onMove(data);
    }

    private void handleYourTurnEvent(final String[] responseArray) {
        this.eventHandler.onYourTurn(responseArray[2]);
    }

    private void handleMatchEvent(final String response) {
        this.eventHandler.onMatch();
    }

    private void handleChallengeEvent(final String response, final String[] responseArray) {
        final String playerName = response.split(" ")[1];
        final int gameNumber = Integer.parseInt(responseArray[2]);
        final int gameName = Integer.parseInt(responseArray[3]);
        this.eventHandler.onChallenge(playerName, gameName, gameNumber);
    }

    private String[] parseMove(final String response) {
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
        return result;
    }
}