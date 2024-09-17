public class Game {
    private Board board;
    private Ship[] ships;
    private int shipsSunk;
    private int shotsFired;
    private int shotsHit;

    public Game(int boardSize, int numShips) {
        board = new Board(boardSize);
        ships = new Ship[numShips];
        shipsSunk = 0;
        shotsFired = 0;
        shotsHit = 0;
    }

    public boolean placeShip(int shipIndex, int x, int y, int size, Direction direction) {
        if (shipIndex < 0 || shipIndex >= ships.length) {
            return false;
        }

        Ship ship = new Ship(x, y, size, direction);
        ships[shipIndex] = ship;
        return true;
    }

    public boolean shoot(int x, int y) {
        shotsFired++;

        for (Ship ship : ships) {
            if (ship != null && ship.isHit(x, y)) {
                shotsHit++;
                if (ship.isSunk()) {
                    shipsSunk++;
                }
                return true;
            }
        }

        return false;
    }

    public boolean isGameOver() {
        return shipsSunk == ships.length;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public int getShotsHit() {
        return shotsHit;
    }

    public int getShipsSunk() {
        return shipsSunk;
    }
}
