package AbstractGame;

public enum Blocks {
    FULL_BLOCK("█"),
    BOTTOM_HALF("▄"),
    TOP_HALF("▀"),
    SMALL_BLOCK("■"),
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