package org.bitshifters.gameclient;

import java.util.logging.Level;

import org.bitshifters.ClientController;
import org.bitshifters.Config;
import org.bitshifters.games.GameTypes;
import org.bitshifters.games.components.GridTransformer;
import org.bitshifters.games.components.Player;
import org.bitshifters.games.stratego.Pawns;
import org.bitshifters.games.stratego.StrategoEngine;
import org.bitshifters.games.stratego.Unit;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.ui.views.StrategoView;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * CLient moet online kunnen spelen
 * Client moet tegen Ai kunnen spelen
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
    private Pawns selectedUnit = null;
    private final Player[] players;
    private int selectedUnitRow;
    private int selectedUnitCol;

    /**
     * Constructor for the StrategoClient
     * @param view the view object
     * @param playerBlue the Player on the blue side
     * @param playerRed the Player on the red side
     * @param boardRows the number of rows on the board
     * @param boardCols the number of columns on the board
     */
    public StrategoClient(StrategoView view, Player playerBlue, Player playerRed, int boardRows, int boardCols) {
        super(GameTypes.Stratego);
        telnet = new TelnetClient(
            config.getValue("host"),
            Integer.parseInt(config.getValue("port")));
        players = new Player[] {playerBlue, playerRed};
        this.view = view;
        this.engine = new StrategoEngine(boardRows, boardCols, players);
        setupGridButtonListeners(view);
    }

    /**
     * Set up each button listener on the grid
     * @param view the view object
     */
    private void setupGridButtonListeners(StrategoView view) {
        for (int row = 0; row < view.getBaseGrid().getButtonGrid().length; row++) {
            for (int col = 0; col < view.getBaseGrid().getButtonGrid()[row].length; col++) {
                setupButtonListener(row, col, view);
            }
        }

        logger.debug("Setting up available units button listeners");
        Button[] buttons = view.getAvailableUnitsButtons().getButtons();
        for (int i = 0; i < buttons.length; i++) {
            setupAvalibleUnitsButtons(i, view);
        }
    }

    /**
     * Set up the button listener
     * @param row
     * @param col
     * @param view1
     */
    private void setupButtonListener(int row, int col, StrategoView view) {
        logger.debug("Setting up button listener at row: " + row + " col: " + col);
        final int finalRow = row;
        final int finalCol = col;
        javafx.scene.control.Button button = view.getBaseGrid().getButtonGrid()[row][col];
        button.addEventHandler(ActionEvent.ACTION, _ -> handleButtonClick(view, finalRow, finalCol));
    }

    /**
     * Handle the button click
     * @param view the view object
     * @param finalRow the final row
     * @param finalCol the final column
     */
    private void handleButtonClick(StrategoView view, final int finalRow, final int finalCol) {
        logger.log(Level.INFO, "Button clicked at row: {0} col: {1}", new Object[]{finalRow, finalCol});
        int index = GT.toIndex(finalRow, finalCol, view.getBaseGrid().getButtonGrid().length);
        if (placingUnits) {
            // TODO: check whether the unit is placed on the right side of the board
            Player player = engine.getActivePlayer();
            placeUnit(finalRow, finalCol, selectedUnit);
            boolean disable = view.getAvailableUnitsButtons().removeUnit(selectedUnit);
            if (disable) {
                this.unitSelected = false;
                this.selectedUnit = null;
            }
            // TODO: telnet send placed unit
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

    /**
     * Set up the available units button listener
     * @param index the index of the button
     * @param view the view object
     */
    private void setupAvalibleUnitsButtons(int index, StrategoView view) {
        logger.debug("Setting up available units button listener at index: " + index);
        javafx.scene.control.Button button = view.getAvailableUnitsButtons().getButtons()[index];
        button.addEventHandler(ActionEvent.ACTION, _ -> {
            handleAvalibleUnitsButtonClick(view, index);
        });
    }

    private void handleAvalibleUnitsButtonClick(StrategoView view, int index) {
        logger.log(Level.INFO, "Available units button clicked at index: {0}", index);
        this.unitSelected = true;
        this.selectedUnit = view.getAvailableUnitsButtons().selectUnitButton(index);
    }

    /**
     * Validate the move
     * @param fromRow the from row
     * @param fromCol the from column
     * @param toRow the to row
     * @param toCol the to column
     * @return true if the move is valid, false otherwise
     */
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
        return engine.validateAttack(attacker, defender, activePlayer);
    }

    /**
     * Make the move
     * @param fromRow the from row
     * @param fromCol the from column
     * @param toRow the to row
     * @param toCol the to column
     */
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

    /***
     * Place a unit on the board
     * @param row the row
     * @param col the column
     * @param pawn the Pawn type
     */
    private void placeUnit(int row, int col, Pawns pawn) {
        logger.log(Level.INFO, "placing unit {0} on row: {1} col: {2}", new Object[]{pawn, row, col});
        if (pawn == null) {
            view.getMainFrame().showPopup("First select a unit to place");
            return;
        }
        Player activePlayer = engine.getActivePlayer();
        view.updateButton(row, col, pawn);
        engine.PlaceUnit(activePlayer, row, col, pawn);
    }

    /**
     * Get the next player
     * @return the next player
     */
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

    /**
     * Handle the turn of the player
     * @param message the message
     */
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

    /**
     * Handle the move of the player and the opponent
     */
    // TODO implement this
    @Override
    public void onMove(String[] data) {
        // Get the move information from the data
        // call makemove
    }

    /**
     * Handle the win of the player
     */
    // TODO implement this
    @Override
    public void onWin() {
        view.getMainFrame().showPopup("You Win");
    }

    /**
     * Handle the lose of the player
     */
    // TODO implement this
    @Override
    public void onLose() {
        view.getMainFrame().showPopup("You Lose");
    }

    /**
     * Handle the draw of the player
     */
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
