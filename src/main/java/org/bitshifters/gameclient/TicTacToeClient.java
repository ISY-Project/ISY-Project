package org.bitshifters.gameclient;

import org.bitshifters.ClientController;
import org.bitshifters.Config;
import org.bitshifters.games.GameTypes;
import org.bitshifters.games.components.Grid;
import org.bitshifters.games.components.GridTransformer;
import org.bitshifters.games.components.Player;
import org.bitshifters.games.tictactoe.Minimax;
import org.bitshifters.games.tictactoe.TTTEngine;
import org.bitshifters.games.tictactoe.TicTacToeCell;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Commands.Move;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.ui.views.TicTacToeView;

import javafx.event.ActionEvent;

/**
 * Client for playing Tic-Tac-Toe.
 * Can play online or against an AI.
 */
public class TicTacToeClient extends GameClient {
    private static final BSLogger logger = new BSLogger(TicTacToeClient.class);
    private static final GridTransformer<Integer> GT = new GridTransformer<>();
    private static final Config config = Config.getInstance();
    private final TelnetClient telnet;
    private final TicTacToeView view;
    private final TTTEngine engine;

    /**
     * Constructs a TicTacToeClient.
     * @param view The TicTacToe view.
     * @param playerX The player X.
     * @param playerO The player O.
     */
    public TicTacToeClient(TicTacToeView view, Player playerX, Player playerO) {
        super(GameTypes.TicTacToe);
        telnet = new TelnetClient(
            config.getValue("host"),
            Integer.parseInt(config.getValue("port")));
        try {
            telnet.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.view = view;
        this.engine = new TTTEngine(playerX, playerO);
        setupGridButtonListeners(view);
    }

    /**
     * Sets up the button listeners for the grid.
     * @param view The TicTacToe view.
     */
    private void setupGridButtonListeners(TicTacToeView view) {
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

    /**
     * Handles the button click event.
     * @param view The TicTacToe view.
     * @param finalRow The row of the clicked button.
     * @param finalCol The column of the clicked button.
     */
    private void handleButtonClick(TicTacToeView view, final int finalRow, final int finalCol) {
        logger.info("Button clicked at row: " + finalRow + " col: " + finalCol);
        int index = GT.toIndex(finalRow, finalCol, view.getBaseGrid().getButtonGrid().length);
        if (this.makeMove(finalRow, finalCol)){
            telnet.send(new Move(index));
        }
    }

    /**
     * Makes a move on the board.
     * @param row The row of the move.
     * @param col The column of the move.
     * @return True if the move is valid, false otherwise.
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
     * Gets the next player.
     * @return The next player.
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

    /**
     * Called when it is the player's turn.
     * @param message The message to display.
     */
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

    /**
     * Converts the grid to a char array.
     * @param grid The TicTacToe grid.
     * @return The char array representation of the grid.
     */
    private char[] getCharGrid(Grid<TicTacToeCell> grid) {
        char[] charGrid = new char[9];
        for (int i = 0; i < grid.getRowCount(); i++) {
            int row = i / 3;
            int col = i % 3;
            charGrid[i] = grid.get(row, col).toString().charAt(0);
        }
        return charGrid;
    }

    /**
     * Handles the move event.
     */
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

    /**
     * Called when the player wins.
     */
    @Override
    public void onWin() {
        this.engine.getPlayerX().incrementScore(3);
        view.getMainFrame().showPopup("You Win");
    }

    /**
     * Called when the player loses.
     */
    @Override
    public void onLose() {
        this.engine.getPlayerO().incrementScore(3);
        view.getMainFrame().showPopup("You Lose");
    }

    /**
     * Called when the game is a draw.
     */
    @Override
    public void onDraw() {
        this.engine.getPlayerX().incrementScore(1);
        this.engine.getPlayerO().incrementScore(1);
        view.getMainFrame().setUpPopup();
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
