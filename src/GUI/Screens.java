package src.GUI;

public enum Screens {
    START_SCREEN("startScreen"),
    BATTLESHIP("battleshipGUI"),
    TTT("tickTackToe");

    private final String screen;

    Screens(String screen) {
        this.screen = screen;
    }

    public String get() {
        return screen;
    }
}
