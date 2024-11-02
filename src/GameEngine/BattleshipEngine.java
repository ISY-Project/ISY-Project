package src.GameEngine;

import java.util.Random;

public class BattleshipEngine extends Engine {
    private BattleshipBoard board;

    public BattleshipEngine(int size, String player1Name, String player2Name) {
        super(size, player1Name, player2Name);
        this.board = new BattleshipBoard(size);
    }

    public BattleshipBoard getBoard() {
        return this.board;
    }

    public void setBoard(BattleshipBoard board) {
        this.board = board;
    }

    public boolean isValidShipPlacement(Ship ship) {
        int x = ship.getX();
        int y = ship.getY();
        int size = ship.getSize();
        boolean isHorizontal = ship.isHorizontal();
        if (y < 0 || x < 0) {
            return false;
        }
        if (isHorizontal) {
            if (y + size > this.board.getSize()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.board.getCell(x, y + i).getValue() != 0) {
                    return false;
                }
            }
        } else {
            if (x + size > this.board.getSize()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.board.getCell(x + i, y).getValue() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidShipPlacement(Ship ship, boolean surroundings) {
        // Check normal placement
        if (!isValidShipPlacement(ship)) {
            return false;
        }

        int x = ship.getX();
        int y = ship.getY();
        int size = ship.getSize();
        boolean isHorizontal = ship.isHorizontal();

        if (x > 0) {
            if (!isValidShipPlacement(new Ship(size, isHorizontal, x - 1, y))) {return false;};
            if (!isValidShipPlacement(new Ship(size, isHorizontal, x - 1, y + 1))) {return false;};
        }
        if (y > 0) {
            if (!isValidShipPlacement(new Ship(size, isHorizontal, x, y - 1))) {return false;};
            if (!isValidShipPlacement(new Ship(size, isHorizontal, x + 1, y - 1))) {return false;};
        }
        if (x > 0 && y > 0) {
            if (!isValidShipPlacement(new Ship(size, isHorizontal, x - 1, y - 1))) {return false;};
        }
        if (!isValidShipPlacement(new Ship(size, isHorizontal, x, y + 1))) {return false;};
        if (!isValidShipPlacement(new Ship(size, isHorizontal, x + 1, y))) {return false;};
        if (!isValidShipPlacement(new Ship(size, isHorizontal, x + 1, y + 1))) {return false;};

        return true;
    }

    public void placeShip(Ship ship) {
        int x = ship.getX();
        int y = ship.getY();
        int size = ship.getSize();
        boolean isHorizontal = ship.isHorizontal();
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                this.board.getCell(x, y + i).setValue(size);
            }
        } else {
            for (int i = 0; i < size; i++) {
                this.board.getCell(x + i, y).setValue(size);
            }
        }
    }

    public boolean isSunk(Ship ship) {
        int x = ship.getX();
        int y = ship.getY();
        int size = ship.getSize();
        boolean isHorizontal = ship.isHorizontal();

        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                if (!this.isHit(x, y + i)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (!this.isHit(x + i, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isHit(int x, int y) {
        return this.board.getCell(x, y).getValue() == -2;
    }

    public boolean shoot(int[] i) {
        return shoot(i[0], i[1]);
    }

    public boolean shoot(int i) {
        int x = i / this.board.getSize();
        int y = i % this.board.getSize();
        return shoot(x, y);
    }

    public boolean shoot(int x, int y) {
        var cell = this.board.getCell(x, y);
        if (cell.getValue() == 0) {
            cell.setValue(-1);
            return false;
        } else {
            cell.setValue(-2);
            return true;
        }
    }

    public int[] getBestMove() {
        // Implement this method
        return new int[] {0, 0};
    }
    public int[] getBestShipSpot(int size, boolean isHorizontal) {
        // Implement this method
        return new int[] {0, 0};
    }

    public static void main(String[] args) {
        BattleshipEngine engine = new BattleshipEngine(10, "Player1", "Player2");
        Random random = new Random();
        int size = 6;

        Ship[] ships = new Ship[] {
            new Ship(size, random.nextBoolean(), 0, 0),
            new Ship(size, random.nextBoolean(), 0, 1),
            new Ship(size, random.nextBoolean(), 0, 2),
            new Ship(size, random.nextBoolean(), 0, 3),
            new Ship(size, random.nextBoolean(), 0, 4),
            new Ship(size, random.nextBoolean(), 1, 0),
            new Ship(size, random.nextBoolean(), 2, 0),
            new Ship(size, random.nextBoolean(), 3, 0),
            new Ship(size, random.nextBoolean(), 4, 0),
            new Ship(size, random.nextBoolean(), 1, 0),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 1, 0),
            new Ship(size, random.nextBoolean(), 2, 0),
            new Ship(size, random.nextBoolean(), 3, 0),
            new Ship(size, random.nextBoolean(), 4, 0),
            new Ship(size, random.nextBoolean(), 1, 0),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 2, 1),
            new Ship(size, random.nextBoolean(), 3, 1),
            new Ship(size, random.nextBoolean(), 4, 1),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 2, 1),
            new Ship(size, random.nextBoolean(), 3, 1),
            new Ship(size, random.nextBoolean(), 4, 1),
            new Ship(size, random.nextBoolean(), 2, 0),
            new Ship(size, random.nextBoolean(), 2, 1),
            new Ship(size, random.nextBoolean(), 2, 2),
            new Ship(size, random.nextBoolean(), 2, 3),
            new Ship(size, random.nextBoolean(), 2, 4),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 2, 2),
            new Ship(size, random.nextBoolean(), 3, 2),
            new Ship(size, random.nextBoolean(), 4, 2),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 2, 2),
            new Ship(size, random.nextBoolean(), 3, 2),
            new Ship(size, random.nextBoolean(), 4, 2),
            new Ship(size, random.nextBoolean(), 3, 0),
            new Ship(size, random.nextBoolean(), 3, 1),
            new Ship(size, random.nextBoolean(), 3, 2),
            new Ship(size, random.nextBoolean(), 3, 3),
            new Ship(size, random.nextBoolean(), 3, 4),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 2, 3),
            new Ship(size, random.nextBoolean(), 3, 3),
            new Ship(size, random.nextBoolean(), 4, 3),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 2, 3),
            new Ship(size, random.nextBoolean(), 3, 3),
            new Ship(size, random.nextBoolean(), 4, 3),
            new Ship(size, random.nextBoolean(), 4, 0),
            new Ship(size, random.nextBoolean(), 4, 1),
            new Ship(size, random.nextBoolean(), 4, 2),
            new Ship(size, random.nextBoolean(), 4, 3),
            new Ship(size, random.nextBoolean(), 4, 4),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 2, 4),
            new Ship(size, random.nextBoolean(), 3, 4),
            new Ship(size, random.nextBoolean(), 4, 4),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 1, 1),
            new Ship(size, random.nextBoolean(), 1, 2),
            new Ship(size, random.nextBoolean(), 1, 3),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 1, 4),
            new Ship(size, random.nextBoolean(), 2, 4),
            new Ship(size, random.nextBoolean(), 3, 4),
            new Ship(size, random.nextBoolean(), 4, 4),
        };
        for (Ship ship : ships) {
            System.out.println(engine.getBoard());
            boolean isValid = engine.isValidShipPlacement(ship, true);
            System.out.println(isValid);
            if (isValid) {
                engine.placeShip(ship);
            }
        }
    }
}
