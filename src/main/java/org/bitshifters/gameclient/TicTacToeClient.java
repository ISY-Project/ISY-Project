package org.bitshifters.gameclient;

import org.bitshifters.ClientController;
import org.bitshifters.Config;
import org.bitshifters.enums.GameTypes;
import org.bitshifters.enums.TicTacToeCell;
import org.bitshifters.games.components.GameClient;
import org.bitshifters.games.components.Grid;
import org.bitshifters.games.components.GridTransformer;
import org.bitshifters.games.components.Player;
import org.bitshifters.games.tictactoe.Minimax;
import org.bitshifters.games.tictactoe.TTTEngine;
import org.bitshifters.logging.BsLogger;
import org.bitshifters.ui.views.TicTacToeView;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.telnet.Commands.Move;

/**
 * CLient moet online kunnen spelen
 * Client moet tegen Ai kunnen spelen
 * 
 */
public class TicTacToeClient extends GameClient {
    private static final BsLogger logger = new BsLogger(TicTacToeClient.class);
    private static final GridTransformer<Integer> GT = new GridTransformer<>();
    private static final Config config = Config.getInstance();
    private final TelnetClient telnet;
    private final TicTacToeView view;
    private final TTTEngine engine;

    public TicTacToeClient(TicTacToeView view, Player playerX, Player playerO) {
        super(GameTypes.TicTacToe);
        telnet = new TelnetClient(
            config.getValue("host"),
            Integer.parseInt(config.getValue("port")));
        this.view = view;
        this.engine = new TTTEngine(playerX, playerO);
        setupGridButtonListeners(view);
    }

    private void setupGridButtonListeners(TicTacToeView view) {
        for (int row = 0; row < view.getBaseGrid().getButtonGrid().length; row++) {
            for (int col = 0; col < view.getBaseGrid().getButtonGrid()[row].length; col++) {
                logger.debug("Setting up button listener at row: " + row + " col: " + col);
                final int finalRow = row;
                final int finalCol = col;
                var button = view.getBaseGrid().getButtonGrid()[row][col];
                button.addEventHandler(null, null);
                // TODO: (_ -> handleButtonClick(view, finalRow, finalCol));
            }
        }
    }

    private void handleButtonClick(TicTacToeView view, final int finalRow, final int finalCol) {
        logger.info("Button clicked at row: " + finalRow + " col: " + finalCol);
        int index = GT.toIndex(finalRow, finalCol, view.getBaseGrid().getButtonGrid().length);
        if (this.makeMove(finalRow, finalCol)){
            telnet.send(new Move(index));
        }
    }

    /**
     * Make a move on the board
     * @param row
     * @param col
     * @return
     */
    public boolean makeMove(int row, int col) {
        logger.info("Making move at row: " + row + " col: " + col);
        Player activePlayer = engine.getActivePlayer();
        Player nextPlayer = getNextPlayer();
        TicTacToeCell PlayerSymbol = engine.getActivePlayer() == engine.getPlayerX() ? TicTacToeCell.X : TicTacToeCell.O;

        if (!engine.validateMove(row, col, activePlayer)) {
            return false;
        }

        engine.makeMove(row, col);
        view.updateButton(row, col, PlayerSymbol);
        engine.setActivePlayer(nextPlayer);
        return true;
    }

    /**
     * Get the next player
     * @return
     */
    private Player getNextPlayer() {
        return engine.getActivePlayer() == engine.getPlayerX() ? engine.getPlayerX() : engine.getPlayerO();
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

    @Override
    public void onYourTurn(String message) {
        if (!ClientController.isComputer) {
            return; // wait on button click
        }
        char[] charGrid = getCharGrid(engine.getGrid(TTTEngine.tttGrid));
        char playerSymbol = engine.getActivePlayer() == engine.getPlayerX() ? 'X' : 'O';
        int bestMove = Minimax.getBestMove(charGrid, playerSymbol);
        telnet.send(new Move(bestMove));
        makeMove(bestMove / 3, bestMove % 3);
    }

    private char[] getCharGrid(Grid<TicTacToeCell> grid) {
        char[] charGrid = new char[9];
        for (int i = 0; i < grid.getRowCount(); i++) {
            int row = i / 3;
            int col = i % 3;
            charGrid[i] = grid.get(row, col).toString().charAt(0);
        }
        return charGrid;
    }

    @Override
    public void onMove(String[] data) {
        // Get the row and column from the data
        int index = Integer.parseInt(data[0]);
        System.out.println("!! Index: " + index);
        int[] coords = GT.toCoordinates(index, 3);
        int row = coords[0];
        int col = coords[1];
        // Make the move
        makeMove(row, col);
    }

    @Override
    public void onWin() {
        this.engine.getPlayerX().incrementScore(3);
        // TODO Wacht tot GUI winstate kan laten zien.
    }

    @Override
    public void onLose() {
        this.engine.getPlayerO().incrementScore(3);
        // TODO Wacht tot GUI winstate kan laten zien.
    }

    @Override
    public void onDraw() {
        this.engine.getPlayerX().incrementScore(1);
        this.engine.getPlayerO().incrementScore(1);
        // TODO Wacht tot GUI winstate kan laten zien.
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
