package src.Hanze;


public class Board {
    public int[][] board;
    public int width;
    public int height;
    public int boardID;

    public Board(int width, int height, int boardID) {
        this.width = width;
        this.height = height;
        this.board = new int[height][width];
        this.boardID = boardID;
    }

    public int get(int i) {
        return this.get(this.iToX(i), this.iToY(i));
    }

    public int get(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height ? this.board[y][x] : -1;
    }

    public void set(int i, int v) {
        this.set(this.iToX(i), this.iToY(i), v);
    }

    public void set(int x, int y, int v) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            this.board[y][x] = v;
            ; // TODO: Change cell to v on gui
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("  ");

        int r;
        for (r = 0; r < this.board[0].length; ++r) {
            if (r != 0) {
                str.append(" ");
            }

            str.append(r);
        }

        for (r = 0; r < this.board.length; ++r) {
            str.append("\n");
            str.append(r).append(" ");

            for (int c = 0; c < this.board[r].length; ++c) {
                if (c != 0) {
                    str.append(" ");
                }

                str.append(this.board[r][c]);
            }
        }

        return str.toString();
    }

    public void fromString(String from) {
        String[] lines = from.split("\n");

        for (int i = 1; i < lines.length; ++i) {
            String[] chars = lines[i].trim().split(" ");

            for (int j = 1; j < chars.length; ++j) {
                switch (chars[j].trim()) {
                    case "1":
                        this.set(j - 1, i - 1, 1);
                        break;
                    case "2":
                        this.set(j - 1, i - 1, 2);
                    case "0":
                        break;
                    default:
                        throw new RuntimeException("What is:" + chars[j]);
                }
            }
        }

    }

    public int xyToI(int x, int y) {
        return y * this.width + x;
    }

    public int iToX(int i) {
        return i % this.width;
    }

    public int iToY(int i) {
        return i / this.width;
    }

    public int countTiles(int value) {
        int n = 0;

        for (int r = 0; r < this.board.length; ++r) {
            for (int c = 0; c < this.board[r].length; ++c) {
                if (this.board[c][r] == value) {
                    ++n;
                }
            }
        }

        return n;
    }
}
