package org.bitshifters.games.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import org.bitshifters.logging.BsLogger;

public class Minimax {
    public enum GameState {
        WIN_X,
        WIN_O,
        TIE,
        ONGOING
    }

    private static int bestMove;
    private static final BsLogger logger = new BsLogger(Minimax.class);

    public Minimax() {
        
    }

    private static GameState gameResult(char[] grid) {
        if ((grid[0] == 'X' && grid[1] == 'X' && grid[2] == 'X') | (grid[3] == 'X' && grid[4] == 'X' && grid[5] == 'X') | (grid[6] == 'X' && grid[7] == 'X' && grid[8] == 'X') | 
            (grid[0] == 'X' && grid[3] == 'X' && grid[6] == 'X') | (grid[1] == 'X' && grid[4] == 'X' && grid[7] == 'X') | (grid[2] == 'X' && grid[5] == 'X' && grid[8] == 'X') | 
            (grid[0] == 'X' && grid[4] == 'X' && grid[8] == 'X') | (grid[2] == 'X' && grid[4] == 'X' && grid[6] == 'X')) {
            return GameState.WIN_X;
        }
        if ((grid[0] == 'O' && grid[1] == 'O' && grid[2] == 'O') | (grid[3] == 'O' && grid[4] == 'O' && grid[5] == 'O') | (grid[6] == 'O' && grid[7] == 'O' && grid[8] == 'O') | 
            (grid[0] == 'O' && grid[3] == 'O' && grid[6] == 'O') | (grid[1] == 'O' && grid[4] == 'O' && grid[7] == 'O') | (grid[2] == 'O' && grid[5] == 'O' && grid[8] == 'O') | 
            (grid[0] == 'O' && grid[4] == 'O' && grid[8] == 'O') | (grid[2] == 'O' && grid[4] == 'O' && grid[6] == 'O')) {
            return GameState.WIN_O;
        }
        if (grid[0] != ' ' && grid[1] != ' ' && grid[2] != ' ' && grid[3] != ' ' && grid[4] != ' ' && grid[5] != ' ' &&grid[6] != ' ' && grid[7] != ' ' && grid[8] != ' ') {
            return GameState.TIE;
        }
        return GameState.ONGOING;
    }

    private static int minimax(char[] grid, boolean isPlayer, char playerSymbol) {
        switch(gameResult(grid)) {
            case WIN_X -> {
                logger.debug("Player X wins with: " + Arrays.toString(grid));
                if (isPlayer) {
                    return -10;
                }
                return 10;
            }
            case WIN_O -> {
                logger.debug("Player O wins with: " + Arrays.toString(grid));
                if (isPlayer) {
                    return -10;
                }
                return 10;
            }
            case TIE -> {
                logger.debug("It's a tie with " + Arrays.toString(grid));
                return 0;
            }
            case ONGOING -> {
                logger.debug("Game is ongoing");
                break;
            }
        }

        char opponentSymbol;
        if (playerSymbol == 'X') {
            opponentSymbol = 'O';
        }
        else {
            opponentSymbol = 'X';
        }

        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i=0; i<9; i++) {
            if (grid[i]==' ') {
                char[] newGrid = grid.clone();
                moves.add(i);
                if (isPlayer) {
                    newGrid[i] = playerSymbol;
                    scores.add(minimax(newGrid, false, playerSymbol));
                }

                else {
                    newGrid[i] = opponentSymbol;
                    scores.add(minimax(newGrid, true, playerSymbol));
                }
            }
        }

        int maxIndex = 0;
        for (int i=0; i<scores.size(); i++) {
            if (isPlayer) {
                if (scores.get(i) > scores.get(maxIndex)) {
                maxIndex = i;
                }
            }
            else {
                if (scores.get(i) < scores.get(maxIndex)) {
                maxIndex = i;
                }
            }
        }

        ArrayList<Integer> optimalMoves = new ArrayList<>();
        for (int i=0; i<scores.size(); i++) {
            if (Objects.equals(scores.get(i), scores.get(maxIndex))) {
                optimalMoves.add(moves.get(i));
            }
        }

        bestMove = optimalMoves.get(new Random().nextInt(optimalMoves.size()));
        return scores.get(maxIndex);
    }

    public static int getBestMove(char[] grid, char playerSymbol) {
        minimax(grid, true, playerSymbol);
        return bestMove;
    }

    public static void main(String[] args) {
        int Win_player_one = 0;
        int Win_player_two = 0;
        int Tie = 0;
        char[] grid;
        int move;
        for (int x = 0; x < 100; x++) {
            grid = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
            while (gameResult(grid).equals(GameState.ONGOING)) {
                move = getBestMove(grid, 'X');
                logger.debug("");
                grid[move] = 'X';
                if (gameResult(grid).equals(GameState.ONGOING)) {
                    logger.debug("");
                    move = getBestMove(grid, 'O');
                    logger.debug("");
                    grid[move] = 'O';
                    logger.debug("");
                }
            }
            logger.debug("");
            System.out.println(gameResult(grid));
            switch(gameResult(grid)) {
                case WIN_X -> {
                    Win_player_one++;
                }
                case WIN_O -> {
                    Win_player_two++;
                }
                case TIE -> {
                    Tie++;
                }
                case ONGOING -> {
                    break;
                }
            }
            for (int i = 0; i < 3; i++) {
                System.out.println(grid[i*3] + " " + grid[i*3+1] + " " + grid[i*3+2]);            
            }
        }
        System.out.println("Wins Player One: " + Win_player_one);
        System.out.println("Wins Player Two: " + Win_player_two);
        System.out.println("Ties: " + Tie);
    }
}