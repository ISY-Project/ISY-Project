package src.GUI;

public class Ship {
    private boolean isVertical = true; // Ship orientation (vertical/horizontal)
    private final int size;

    public Ship(int size) {
        this.size = size;
        this.isVertical = true;
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
