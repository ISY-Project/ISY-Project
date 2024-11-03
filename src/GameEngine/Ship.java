package src.GameEngine;

public class Ship {
    private int size;
    private boolean isHorizontal;
    private int x;
    private int y;

    public Ship(int size, boolean isHorizontal, int x, int y) {
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.x = x;
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
