package src.ALG;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Minimax {
    private static int bestMove;

    public Minimax() {
        
    }

    private static GameState gameResult(char[] grid) {
        if ((grid[0] == 'X' & grid[1] == 'X' & grid[2] == 'X') | (grid[3] == 'X' & grid[4] == 'X' & grid[5] == 'X') | (grid[6] == 'X' & grid[7] == 'X' & grid[8] == 'X') | 
            (grid[0] == 'X' & grid[3] == 'X' & grid[6] == 'X') | (grid[1] == 'X' & grid[4] == 'X' & grid[7] == 'X') | (grid[2] == 'X' & grid[5] == 'X' & grid[8] == 'X') | 
            (grid[0] == 'X' & grid[4] == 'X' & grid[8] == 'X') | (grid[2] == 'X' & grid[4] == 'X' & grid[6] == 'X')) {
            return GameState.WIN_X;
        }
        if ((grid[0] == 'O' & grid[1] == 'O' & grid[2] == 'O') | (grid[3] == 'O' & grid[4] == 'O' & grid[5] == 'O') | (grid[6] == 'O' & grid[7] == 'O' & grid[8] == 'O') | 
            (grid[0] == 'O' & grid[3] == 'O' & grid[6] == 'O') | (grid[1] == 'O' & grid[4] == 'O' & grid[7] == 'O') | (grid[2] == 'O' & grid[5] == 'O' & grid[8] == 'O') | 
            (grid[0] == 'O' & grid[4] == 'O' & grid[8] == 'O') | (grid[2] == 'O' & grid[4] == 'O' & grid[6] == 'O')) {
            return GameState.WIN_O;
        }
        if (grid[0] != ' ' & grid[1] != ' ' & grid[2] != ' ' & grid[3] != ' ' & grid[4] != ' ' & grid[5] != ' ' &grid[6] != ' ' & grid[7] != ' ' & grid[8] != ' ') {
            return GameState.TIE;
        }
        return GameState.ONGOING;
    }

    private static int minimax(char[] grid, boolean isplayer, char playerSymbol) {
        switch(gameResult(grid)) {
            case WIN_X -> {
                // System.out.println("X Won");
                if (isplayer) {
                    return -10;
                }
                return 10;
            }
            case WIN_O -> {
                // System.out.println("O Won");
                if (isplayer) {
                    return -10;
                }
                return 10;
            }
            case TIE -> {
                // System.out.println("It's a Tie");
                return 0;
            }
            case ONGOING -> {
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
                char[] newgrid = grid.clone();
                moves.add(i);
                if (isplayer) {
                    newgrid[i] = playerSymbol;
                    scores.add(minimax(newgrid, false, playerSymbol));
                }

                else {
                    newgrid[i] = opponentSymbol;
                    scores.add(minimax(newgrid, true, playerSymbol));
                }
            }
        }

        int maxIndex = 0;
        for (int i=0; i<scores.size(); i++) {
            if (isplayer) {
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
                // System.out.println(move);
                grid[move] = 'X';
                if (gameResult(grid).equals(GameState.ONGOING)) {
                    // System.out.println("Game is ongoing");
                    move = getBestMove(grid, 'O');
                    // System.out.println(move);
                    grid[move] = 'O';
                    // System.out.println("Game is ongoing");
                }
            }
            // System.out.println("Game is not ongoing");
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
