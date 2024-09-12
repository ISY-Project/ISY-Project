package AbstractGame;

public class Score {
    private int current;
    private int total;

    public Score() {
        this.current = 0;
        this.total = 0;
    }

    public void add(int points) {
        this.current += points;
        this.total += points;
    }

    public void subtract(int points) {
        this.current -= points;
        this.total += points;
    }

    public void reset() {
        this.current = 0;
    }

    public int getCurrent() {
        return this.current;
    }

    public int getTotal() {
        return this.total;
    }
}
