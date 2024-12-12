package org.bitshifters.gameclient;

import org.bitshifters.ClientController;
import org.bitshifters.Config;
import org.bitshifters.games.GameTypes;
import org.bitshifters.games.components.GridTransformer;
import org.bitshifters.games.components.Player;
import org.bitshifters.games.stratego.StrategoEngine;
import org.bitshifters.games.stratego.Unit;
import org.bitshifters.games.stratego.Pawns;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.ui.views.StrategoView;

import javafx.event.ActionEvent;

/**
 * CLient moet online kunnen spelen
 * Client moet tegen Ai kunnen spelen
 * 
 */
public class StrategoClient extends GameClient {
    private static final BSLogger logger = new BSLogger(TicTacToeClient.class);
    private static final GridTransformer<Integer> GT = new GridTransformer<>();
    private static final Config config = Config.getInstance();
    private final TelnetClient telnet;
    private final StrategoView view;
    private final StrategoEngine engine;
    private boolean placingUnits = true;
    private boolean unitSelected = false;
    private Player[] players;
    private int selectedUnitRow;
    private int selectedUnitCol;
    private int boardSize;

    public StrategoClient(StrategoView view, Player playerBlue, Player playerRed, int boardSize) {
        super(GameTypes.Stratego);
        telnet = new TelnetClient(
            config.getValue("host"),
            Integer.parseInt(config.getValue("port")));
        Player[] players = {playerBlue, playerRed};
        this.view = view;
        this.engine = new StrategoEngine(players);
        this.boardSize = boardSize;
        setupGridButtonListeners(view);
    }

    private void setupGridButtonListeners(StrategoView view) {
        for (int row = 0; row < view.getBaseGrid().getButtonGrid().length; row++) {
            for (int col = 0; col < view.getBaseGrid().getButtonGrid()[row].length; col++) {
                logger.debug("Setting up button listener at row: " + row + " col: " + col);
                final int finalRow = row;
                final int finalCol = col;
                var button = view.getBaseGrid().getButtonGrid()[row][col];
                button.addEventHandler(ActionEvent.ACTION, (_ -> handleButtonClick(view, finalRow, finalCol)));
            }
        }
    }

    private void handleButtonClick(StrategoView view, final int finalRow, final int finalCol) {
        logger.info("Button clicked at row: " + finalRow + " col: " + finalCol);
        int index = GT.toIndex(finalRow, finalCol, view.getBaseGrid().getButtonGrid().length);
        // TODO UI interaction to place units
        if (placingUnits) {
            Player player = engine.getActivePlayer();
            // get or store unit to place
            // call placeUnit
            // telnet send placed unit
            if (engine.validateAllUnitsPlaced(player)) {
                placingUnits = false;
                engine.startGame(null);
            }
            return;
        }
        if (unitSelected) {
            int fromIndex = GT.toIndex(selectedUnitRow, selectedUnitCol, view.getBaseGrid().getButtonGrid().length);

            if (validateMove(selectedUnitRow, selectedUnitCol, finalRow, finalCol)){
                makeMove(selectedUnitRow, selectedUnitCol, finalRow, finalCol);
                // TODO telnet for stratego move
                // telnet send move
            }
            unitSelected = false;
            return;
        }
        unitSelected = true;
        selectedUnitRow = finalRow;
        selectedUnitCol = finalCol;
    }

    private boolean validateMove(int fromRow, int fromCol, int toRow, int toCol) {
        Player activePlayer = engine.getActivePlayer();
        if (!engine.validateMove(fromRow, fromCol, toRow, toCol, activePlayer)) {
            return false;
        }
        if (engine.getCell(toRow, toCol, StrategoEngine.GameGrid) == null) {
            return true;
        }
        Unit attacker = engine.getCell(fromRow, fromCol, activePlayer);
        Unit defender = engine.getCell(toRow, toCol, activePlayer);
        if (!engine.validateAttack(attacker, defender, activePlayer)) {
            return false;
        }
        return true;
    }

    private void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        logger.info("Making move from row: " + fromRow + " col: " + fromCol + " to row: " + toRow + " col: " + toCol);
        Player activePlayer = engine.getActivePlayer();
        Player nextPlayer = getNextPlayer();

        if (engine.getCell(toRow, toCol, StrategoEngine.GameGrid) == null) {
            view.movePawn(fromRow, fromCol, toRow, toCol);
            engine.moveUnit(fromRow, fromCol, toRow, toCol, activePlayer);
        }
        // TODO attack happened, needs server result of attack
        else {

        }
        engine.setActivePlayer(nextPlayer);
    }

    private void placeUnit(int row, int col, Pawns pawn) {
        logger.info("placing unit " + pawn + " on row: " + row + " col: " + col);
        Player activePlayer = engine.getActivePlayer();
        view.updateButton(row, col, pawn);
        engine.PlaceUnit(activePlayer, row, col, pawn);
    }

    private Player getNextPlayer() {
        return engine.getActivePlayer() == players[0] ? players[1] : players[0];
    }

    @Override
    public void onChallenge(String playerName, int game, int gameNumber) {
        // Accept the challenge, and start the game
        throw new UnsupportedOperationException("Unimplemented method 'onChallenge'");
    }

    @Override
    public void onCancel(int gameNumber) {
        // Cancel the game
        throw new UnsupportedOperationException("Unimplemented method 'onCancel'");
    }

    @Override
    public void onMatch() {
        // Als de game begint, start de game
        throw new UnsupportedOperationException("Unimplemented method 'onMatch'");
    }

    // TODO algorithm
    @Override
    public void onYourTurn(String message) {
        if (!ClientController.isComputer) {
            return; // wait on button click
        }
        // get algorithm move 
        // telnet send move
        // call makeMove
    }

    // TODO implement this
    @Override
    public void onMove(String[] data) {
        // Get the move information from the data
        // call makemove
    }

    // TODO implement this
    @Override
    public void onWin() {
        view.getMainFrame().showPopup("You Win");
    }

    // TODO implement this
    @Override
    public void onLose() {
        view.getMainFrame().showPopup("You Lose");
    }

    // TODO implement this
    @Override
    public void onDraw() {
        view.getMainFrame().showPopup("Draw");
    }

    @Override
    public void onHelp(String message) {
        // Niet nodig?
        throw new UnsupportedOperationException("Unimplemented method 'onHelp'");
    }

    @Override
    public void onError(String message) {
        // Niet nodig?
        throw new UnsupportedOperationException("Unimplemented method 'onError'");
    }

    @Override
    public void onMessage(String message) {
        // Niet nodig?
        throw new UnsupportedOperationException("Unimplemented method 'onMessage'");
    }
}
