package src.GUI.Components;

public class Ship {
    private boolean isVertical = true; // Ship orientation (vertical/horizontal)
    private final int size;

    public Ship(int size) {
        this.size = size;
        this.isVertical = true;
    }

    public Ship(int size, boolean isVertical) {
        this.size = size;
        this.isVertical = isVertical;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean isVertical) {
        this.isVertical = isVertical;
    }

    public int getSize() {
        return this.size;
    }

    public void rotate() {
        isVertical = !isVertical;  // Toggle orientation
    }
}
