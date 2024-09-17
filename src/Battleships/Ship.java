public class Ship {
    private int x;
    private int y;
    private int size;
    private Direction direction;
    private boolean[] hits;

    public Ship(int x, int y, int size, Direction direction) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.direction = direction;
        this.hits = new boolean[size];
    }
}
