package AbstractGame;

public enum Blocks {
    UNKNOWN("■"),
    HIT("X"),
    MISS("□"),
    SEA("■"),
    DESTROYED("*")
    ;

    private final String text;

    Blocks(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}