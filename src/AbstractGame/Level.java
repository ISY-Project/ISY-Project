package AbstractGame;

public class Level {
    private String name;
    private int difficulty;
    private Timer timer;

    public Level(String name, int difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.timer = new Timer();
    }
}
