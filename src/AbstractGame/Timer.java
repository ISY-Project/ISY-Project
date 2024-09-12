package AbstractGame;

public class Timer {
    private long start_time;

    public Timer() {
        this.start_time = (System.currentTimeMillis() / 1000L);
    }
}
