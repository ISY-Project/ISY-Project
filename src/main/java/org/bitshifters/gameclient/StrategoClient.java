package org.bitshifters.gameclient;

import java.util.logging.Level;

import org.bitshifters.ClientController;
import org.bitshifters.games.GameTypes;
import org.bitshifters.games.components.GridTransformer;
import org.bitshifters.games.components.Player;
import org.bitshifters.games.stratego.Pawns;
import org.bitshifters.games.stratego.StrategoEngine;
import org.bitshifters.games.stratego.Unit;
import org.bitshifters.games.stratego.UnitSet;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Commands.Move;
import org.bitshifters.telnet.Commands.Place;
import org.bitshifters.ui.components.PawnButtonInformation;
import org.bitshifters.ui.enums.Screens;
import org.bitshifters.ui.views.StrategoView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * CLient moet online kunnen spelen
 * Client moet tegen Ai kunnen spelen
 */
public class StrategoClient extends GameClient {
    private static final BSLogger logger = new BSLogger(StrategoClient.class);
    private static final GridTransformer<Integer> GT = new GridTransformer<>();
    private final static Player opponentPlayer = ClientController.getOpponentPlayer();
    private final StrategoView view;
    private final StrategoEngine engine;
    private boolean placingUnits = true; // is placing mode active yes/no
    private boolean unitSelected = false; // is unit selected for movement yes/no
    private Pawns selectedUnit = null; // type of unit selected for placement
    private final Player[] players;
    private int selectedUnitRow; // selected unit row for move
    private int selectedUnitCol; // selected unit col for move
    private int storedFromRow; // move stored for battleresult
    private int storedFromCol; // move stored for battleresult
    private int storedToRow; // move stored for battleresult
    private int storedToCol; // move stored for battleresult


    public enum CombatResult {
        WIN,
        LOSS,
        TIE
    }

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
        players = new Player[] {playerBlue, playerRed};
        this.view = view;
        this.engine = new StrategoEngine(boardRows, boardCols, players);
        setupGridButtonListeners(view);
        if (view.isSmallVersion()) {
            engine.setUnitCounts(UnitSet.EIGHT);
        }
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
            setupAvailableUnitsButtons(i, view);
        }

        view.getNavigationButtons().getResetButton().addEventHandler(ActionEvent.ACTION, _ -> resetGrids());

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

        if (engine.getActivePlayer() == null) {
            view.getMainFrame().showPopup("Game has not started yet");
            return;
        }

        if (placingUnits) { // placing fase
            if (!validatePlaceUnit(finalRow, finalCol, selectedUnit)) {
                return;
            }
            placeUnit(finalRow, finalCol, selectedUnit);
            selectedUnit = view.getAvailableUnitsButtons().removeUnit(selectedUnit);
            checkStartGame();
            return;
        }
        // moving fase
        if (unitSelected) { // move unit (click on second button)

            if (selectedUnitCol == finalCol && selectedUnitRow == finalRow) { // deselect unit
                view.getBaseGrid().deselectButton(selectedUnitRow, selectedUnitCol);
                unitSelected = false;
                selectedUnitRow = -1;
                selectedUnitCol = -1;
                return;
            }
            if (validateMove(selectedUnitRow, selectedUnitCol, finalRow, finalCol)){
                makeMove(selectedUnitRow, selectedUnitCol, finalRow, finalCol);
                int size = engine.getTotalCols();
                telnet.send(new Move(
                    GT.toIndex(selectedUnitRow, selectedUnitCol, size),
                    GT.toIndex(finalRow, finalCol, size)));
            }
            else {
                view.getMainFrame().showPopup("Invalid move, the selected unit cannot move there");
            }
            view.getBaseGrid().deselectButton(selectedUnitRow, selectedUnitCol);
            unitSelected = false;
            selectedUnitRow = -1;
            selectedUnitCol = -1;
        } else { // select unit (first click)
            // DO NOT FIX THE LAKES, THEY ARE NOT BUGS, THEY ARE FEATURES
            if (
                engine.getCell(finalRow, finalCol, StrategoEngine.GameGrid) == null
                || engine.getCell(finalRow, finalCol, StrategoEngine.GameGrid).getRank() == Pawns.UNKNOWN
                || engine.getCell(finalRow, finalCol, StrategoEngine.GameGrid).getRank() == Pawns.LAKE
            ) {
                return; // no unit to select
            }
            view.getBaseGrid().selectButton(finalRow, finalCol);
            unitSelected = true;
            selectedUnitRow = finalRow;
            selectedUnitCol = finalCol;
        }
    }

    /**
     * Set up the available units button listener
     * @param index the index of the button
     * @param view the view object
     */
    private void setupAvailableUnitsButtons(int index, StrategoView view) {
        logger.debug("Setting up available units button listener at index: " + index);
        javafx.scene.control.Button button = view.getAvailableUnitsButtons().getButtons()[index];
        button.addEventHandler(ActionEvent.ACTION, _ -> {
            handleAvalibleUnitsButtonClick(view, index);
        });
    }

    private void handleAvalibleUnitsButtonClick(StrategoView view, int index) {
        logger.log(Level.INFO, "Available units button clicked at index: {0}", index);
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
        logger.debug("Validating move from row: " + fromRow + " col: " + fromCol + " to row: " + toRow + " col: " + toCol);
        Player activePlayer = engine.getActivePlayer();
        if (!engine.validateMove(fromRow, fromCol, toRow, toCol, activePlayer)) {
            return false;
        }
        if (engine.getCell(toRow, toCol, StrategoEngine.GameGrid) == null) {
            return true;
        }
        Unit attacker = engine.getCell(fromRow, fromCol, StrategoEngine.GameGrid);
        Unit defender = engine.getCell(toRow, toCol, StrategoEngine.GameGrid);
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
        removeLastEnemyInformation();
        
        if (engine.getCell(toRow, toCol, StrategoEngine.GameGrid) == null) {
            view.movePawn(fromRow, fromCol, toRow, toCol);
            engine.moveUnit(fromRow, fromCol, toRow, toCol, activePlayer);
            engine.setActivePlayer(nextPlayer);
        }
        // Attack happened, store move for battle result
        else {
            this.storedFromRow = fromRow;
            this.storedFromCol = fromCol;
            this.storedToRow = toRow;
            this.storedToCol = toCol;
        }
    }

    /**
     * Remove the last enemy unit rank information from the gui
     */
    private void removeLastEnemyInformation() {
        Unit unit = engine.getCell(storedToRow, storedToCol, StrategoEngine.GameGrid);
        if (unit == null || unit.getPlayer() != opponentPlayer || ClientController.isComputer) return;
        view.updateButton(storedToRow, storedToCol, new PawnButtonInformation(Pawns.UNKNOWN, true));
    }

    /**
     * Apply the result of the attack from the attackers perspective
     * @param result the result of the attack
     */
    // TODO test
    private void applyAttackResult(CombatResult result) {
        Player activePlayer = engine.getActivePlayer();

        if (result == CombatResult.WIN) {
            // essentially no different from moving into an empty space
            view.movePawn(storedFromRow, storedFromCol, storedToRow, storedToCol);
            engine.moveUnit(storedFromRow, storedFromCol, storedToRow, storedToCol, activePlayer);
        }
        else if (result == CombatResult.TIE) {
            // killing both units
            view.updateButton(storedFromRow, storedFromCol, new PawnButtonInformation(null, false));
            view.updateButton(storedToRow, storedToCol, new PawnButtonInformation(null, false));
            engine.moveUnit(storedFromRow, storedFromCol, storedToRow, storedToCol, activePlayer); // just to update movementtracker and turn count
            engine.killUnit(storedToRow, storedToCol);
        }
        else if (result == CombatResult.LOSS) {
            // killing the attacker, is a little weird because of having to update the movementtracker
            view.updateButton(storedFromRow, storedFromCol, new PawnButtonInformation(null, false));
            engine.moveUnit(storedFromRow, storedFromCol, storedFromRow, storedFromCol, activePlayer); // just to update movementtracker and turn count
            engine.killUnit(storedFromRow, storedFromCol);
        }
        engine.setActivePlayer(getNextPlayer());
    }

    /**
     * Validate if the unit can be placed there.
     * @param row the row
     * @param col the column
     * @param pawn the pawn to be placed
     * @return true if the placement is valid
     */
    private boolean validatePlaceUnit(int row, int col, Pawns pawn) {
        Player activePlayer = engine.getActivePlayer();

        if (activePlayer == ClientController.getOpponentPlayer()){
            view.getMainFrame().showPopup("It is not your turn");
            return false;
        }

        int engineRow = (activePlayer.equals(players[1])) ? engine.rotateRow(row) - (engine.getTotalRows()/2 + 1) : row - (engine.getTotalRows()/2 + 1); // rotate the board for player 1
        int engineCol = (activePlayer.equals(players[1])) ? engine.rotateCol(col) : col; // rotate the board for player 1

        if (pawn == null) {
            view.getMainFrame().showPopup("First select a unit to place");
            return false;
        } else if (!engine.validatePlaceUnit(activePlayer, engineRow, engineCol)) {
            view.getMainFrame().showPopup("You are not able to place a unit there");
            return false;
        }
        return true;
    }

    /**
     * check if all units are placed
     * @return true if all units are placed
     */
    private boolean validateAllUnitsPlaced() {
        Player activePlayer = engine.getActivePlayer();
        Player nextPlayer = getNextPlayer();
        if (
            engine.validateAllUnitsPlaced(activePlayer)
            && view.isPlacingDone() // should always be true if validateAllUnitsPlaced is true
        ) {
            placingUnits = false;
            view.setPlacingMode(placingUnits);
            logger.info("Placing units done, waiting on enemy");
            view.getMainFrame().showPopup("All units are placed, waiting for the other player");
            engine.setActivePlayer(nextPlayer);
            return false;
        }
        return true;
    }

    /***
     * Place a unit on the board
     * @param row the row
     * @param col the column
     * @param pawn the Pawn type
     * @return true if all units are placed after this placement
     */
    private boolean placeUnit(int row, int col, Pawns pawn) {
        logger.log(Level.INFO, "placing unit {0} on row: {1} col: {2}", new Object[]{pawn, row, col});
        Player activePlayer = engine.getActivePlayer();

        int engineRow = (activePlayer.equals(players[1])) ? engine.rotateRow(row) - (engine.getTotalRows()/2 + 1) : row - (engine.getTotalRows()/2 + 1); // rotate the board for player 1
        int engineCol = (activePlayer.equals(players[1])) ? engine.rotateCol(col) : col; // rotate the board for player 1

        view.updateButton(row, col, new PawnButtonInformation(pawn, activePlayer.equals(players[1]))); // update the button with the pawn, should only be done for the active player
        engine.PlaceUnit(activePlayer, engineRow, engineCol, pawn);
        telnet.send(new Place(pawn.getName(), GT.toIndex(row, col, engine.getTotalCols())));

        return validateAllUnitsPlaced();
    }

    /**
     * Start the game if all units on both sides have been placed
     */
    private void checkStartGame() {
        if (
            !engine.validateAllUnitsPlaced(engine.getActivePlayer())
            || !engine.validateAllUnitsPlaced(getNextPlayer())
        ) {
            return;
        }
        view.setPlacingMode(false);
        placingUnits = false;
        logger.info("Placing units done, starting game");
        engine.startGame(players);
    }

    /**
     * Get the next player
     * @return the next player
     */
    private Player getNextPlayer() {
        Player activePlayer = engine.getActivePlayer();
        if (activePlayer == null) {
            return players[1];
        }
        return activePlayer == players[0] ? players[1] : players[0];
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
        setScreen();
        engine.setActivePlayer(getNextPlayer());
    }

    private void setScreen() {
        if (view.isSmallVersion()) {
            view.getMainFrame().showScreen(Screens.STRATEGOEIGHT);
        } else {
            view.getMainFrame().showScreen(Screens.STRATEGOTEN);
        }
    }

    /**
     * Handle the turn of the player
     * @param message the message
     */
    // TODO algorithm
    @Override
    public void onYourTurn(String message) {
        engine.setActivePlayer(players[0]);
        view.setPlacingMode(placingUnits);
        if (placingUnits) {
            if (!ClientController.isComputer) return;
            // while placing units
            // get algorithm placement
            // telnet send placement
            // call placeUnit
            return;
        }
        // get algorithm move 
        // telnet send move
        // call makeMove
    }

    /**
     * Handle the placement of the opponent
     * @param index the index of the placement
     */
    @Override
    public void onPlaced(int index) {
        int[] coord = GT.toCoordinates(index, engine.getTotalCols());
        int row = coord[0];
        int col = coord[1];
        Pawns rank = Pawns.UNKNOWN;
        PawnButtonInformation pawn = new PawnButtonInformation(rank, true);

        view.updateButton(row, col, pawn);
        engine.PlaceUnit(opponentPlayer, row, col, rank);
        checkStartGame();
    }

    /**
     * Handle the move of the opponent
     * @param data the from index and to index of the movement
     */
    // TODO test this
    @Override
    public void onMove(String[] data) {
        // Get the rows and columns from the data
        int fromIndex = Integer.parseInt(data[0]);
        int toIndex = Integer.parseInt(data[1]);
        System.out.println("!! Move: " + fromIndex + " to " + toIndex);
        int[] fromCoords = GT.toCoordinates(fromIndex, engine.getTotalCols());
        int[] toCoords = GT.toCoordinates(toIndex, engine.getTotalCols());
        int fromRow = fromCoords[0];
        int fromCol = fromCoords[1];
        int toRow = toCoords[0];
        int toCol = toCoords[1];
        // Make the move
        makeMove(fromRow, fromCol, toRow, toCol);
    }

    /**
     * Handle the attack result
     * @param defender the enemy unit
     * @param result the result of the attack
     */
    public void onAttackResult(Pawns defender, CombatResult result) {
        view.updateButton(storedToRow, storedToCol, new PawnButtonInformation(defender, true));
        engine.PlaceGridUnit(opponentPlayer, storedToRow, storedToCol, defender);

        applyAttackResult(result);
    }

    /**
     * Handle the defense result
     * @param attacker the enemy unit
     * @param result the result of the defense
     */
    public void onDefenseResult(Pawns attacker, CombatResult result) {
        view.updateButton(storedFromRow, storedFromCol, new PawnButtonInformation(attacker, true));
        engine.PlaceGridUnit(opponentPlayer, storedFromRow, storedFromCol, attacker);

        if (result == CombatResult.TIE) {
            applyAttackResult(result);
        }
        else if (result == CombatResult.LOSS) {
            applyAttackResult(CombatResult.WIN);
        }
        else if (result == CombatResult.WIN){
            applyAttackResult(CombatResult.LOSS);
        }
    }

    /**
     * Reset the game if the game ended
     */
    private void resetGrids() {
        view.resetView();
        if (engine.getActivePlayer() != null) {
            engine.resetGrid(engine.getActivePlayer());
            engine.resetGrid(getNextPlayer());
        }
        engine.resetGrid(StrategoEngine.GameGrid);
        // ClientController.setSelectedClient(this);
    }

    /**
     * Handle the win of the player
     */
    @Override
    public void onWin() {
        resetGrids();
        Platform.runLater(() -> view.getMainFrame().showPopup("You Win"));
    }

    /**
     * Handle the lose of the player
     */
    @Override
    public void onLose() {
        resetGrids();
        Platform.runLater(() -> view.getMainFrame().showPopup("You Lose"));
    }

    /**
     * Handle the draw of the player
     */
    @Override
    public void onDraw() {
        resetGrids();
        Platform.runLater(() -> view.getMainFrame().showPopup("Draw"));
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
