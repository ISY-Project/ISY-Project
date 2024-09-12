package AbstractGame;

public class Timer {
    private long startTime, totalTime;

    public Timer() {
        this.startTime = (long) 0.0;
    }

    public void StartTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public void PauseTimer() {
        this.totalTime += System.currentTimeMillis() - this.startTime;
        this.startTime = (long) 0.0;
    }

    public void ResumeTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public void ResetTimer() {
        this.startTime = (long) 0.0;
        this.totalTime = (long) 0.0;
    }

    public void StopTimer() {
        this.totalTime += System.currentTimeMillis() - this.startTime;
        this.startTime = (long) 0.0;
    }

    public long GetTime() {
        return this.totalTime;
    }
}
