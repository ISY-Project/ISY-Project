package src.GameEngine;

public class Cell<T> {
    private final int x;
    private final int y;
    private T Value;

    public Cell(int x, int y, T value) {
        this.x = x;
        this.y = y;
        this.Value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getCoordinates() {
        return new int[]{x, y};
    }

    public T getValue() {
        return Value;
    }

    public void setValue(T value) {
        Value = value;
    }
}
