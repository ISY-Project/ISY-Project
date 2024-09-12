package AbstractGame;

public class Player {
    private Score totalScore;
    private Gui gui;

    public Player() {
        this.totalScore = new Score();
        this.gui = new Gui();
    }
}
