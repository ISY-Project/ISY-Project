import java.util.ArrayList;
import java.util.Random;

public class Minimax {
    private static int bestMove;

    public Minimax() {
        
    }

    private static String gameResult(char[] grid) {
        if ((grid[0] == 'X' & grid[1] == 'X' & grid[2] == 'X') | (grid[3] == 'X' & grid[4] == 'X' & grid[5] == 'X') | (grid[6] == 'X' & grid[7] == 'X' & grid[8] == 'X') | 
            (grid[0] == 'X' & grid[3] == 'X' & grid[6] == 'X') | (grid[1] == 'X' & grid[4] == 'X' & grid[7] == 'X') | (grid[2] == 'X' & grid[5] == 'X' & grid[8] == 'X') | 
            (grid[0] == 'X' & grid[4] == 'X' & grid[8] == 'X') | (grid[2] == 'X' & grid[4] == 'X' & grid[6] == 'X')) {
            return "X won";
        }
        if ((grid[0] == 'O' & grid[1] == 'O' & grid[2] == 'O') | (grid[3] == 'O' & grid[4] == 'O' & grid[5] == 'O') | (grid[6] == 'O' & grid[7] == 'O' & grid[8] == 'O') | 
            (grid[0] == 'O' & grid[3] == 'O' & grid[6] == 'O') | (grid[1] == 'O' & grid[4] == 'O' & grid[7] == 'O') | (grid[2] == 'O' & grid[5] == 'O' & grid[8] == 'O') | 
            (grid[0] == 'O' & grid[4] == 'O' & grid[8] == 'O') | (grid[2] == 'O' & grid[4] == 'O' & grid[6] == 'O')) {
            return "O won";
        }
        if (grid[0] != ' ' & grid[1] != ' ' & grid[2] != ' ' & grid[3] != ' ' & grid[4] != ' ' & grid[5] != ' ' &grid[6] != ' ' & grid[7] != ' ' & grid[8] != ' ') {
            return "tie";
        }
        return "game not ended";
    }

    private static int minimax(char[] grid, boolean isplayer, char playerSymbol) {
        if (gameResult(grid) == "X won" | gameResult(grid) == "O won") {
            if (isplayer) {
                return -10;
            }
            return 10;
        }
        else if (gameResult(grid) == "tie") {
            return 0;
        }

        char opponentSymbol;
        if (playerSymbol == 'X') {
            opponentSymbol = 'O';
        }
        else {
            opponentSymbol = 'X';
        }

        ArrayList<Integer> scores = new ArrayList<Integer>();
        ArrayList<Integer> moves = new ArrayList<Integer>();
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

        ArrayList<Integer> optimalMoves = new ArrayList<Integer>();
        for (int i=0; i<scores.size(); i++) {
            if (scores.get(i) == scores.get(maxIndex)) {
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
        char[] grid = {' ', 'O', 'X', ' ', 'O', ' ', 'X', ' ', ' '};
        System.out.println(getBestMove(grid, 'X'));
    }
}
