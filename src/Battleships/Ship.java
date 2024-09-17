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

    public boolean isHit(int x, int y) {
        if (y == this.y && x >= this.x && x < this.x + size) {
                hits[x - this.x] = true;
                return true;
        } else if (x == this.x && y >= this.y && y < this.y + size) {
                hits[y - this.y] = true;
                return true;
        }
        if (x == this.x && y >= this.y && y < this.y + size) {
                hits[y - this.y] = true;
                return true;
        } else if (y == this.y && x >= this.x && x < this.x + size) {
                hits[x - this.x] = true;
                return true;
        }
        return false;
    }
}
