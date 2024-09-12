package AbstractGame;

public class Timer {
    private long startTime, totalTime;

    public Timer() {
        this.startTime = (long) 0.0;
    }

    private long CalculateTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public void StartTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public void PauseTimer() {
        this.totalTime += CalculateTime();
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
        this.totalTime += CalculateTime();
        this.startTime = (long) 0.0;
    }

    public long GetTime() {
        if (this.startTime > 0.0) {
            return this.totalTime + CalculateTime();
        }
        return this.totalTime;
    }
}
