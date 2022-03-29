//Stopwatch class provided by EdHurtig

public class Stopwatch {
    private boolean running;
    private boolean paused;
    private long start;
    private long pausedStart;
    private long end;

    //Constructor
    public Stopwatch() {
        this.pausedStart = 0;
        this.start = 0;
        this.end = 0;
    }

    //Determines if running
    public boolean isRunning() {
        return running;
    }

    //Determines if paused
    public boolean isPaused() {
        return paused;
    }

    //Starts stopwatch
    public void start() {
        start = System.nanoTime();
        running = true;
        paused = false;
        pausedStart = -1;
    }

    //Stops stopwatch
    public long stop() {
        if (!isRunning()) {
            return -1;
        } else if (isPaused()) {
            running = false;
            paused = false;

            return pausedStart - start;
        } else {
            end = System.nanoTime();
            running = false;
            return end - start;
        }
    }

    //Pauses Stopwatch
    public long pause() {
        if (!isRunning()) {
            return -1;
        } else if (isPaused()) {
            return (pausedStart - start);
        } else {
            pausedStart = System.nanoTime();
            paused = true;
            return (pausedStart - start);
        }
    }

    //Resumes from pause
    public void resume() {
        if (isPaused() && isRunning()) {
            start = System.nanoTime() - (pausedStart - start);
            paused = false;
        }
    }

    //returns time elapsed
    public long elapsed() {
        if (isRunning()) {
            if (isPaused())
                return (pausedStart - start);
            return (System.nanoTime() - start);
        } else
            return (end - start);
    }

    //returns a string in seconds
    public String toString() {
        long enlapsed = elapsed();
        return ((double) enlapsed / 1000000000.0) + " Seconds";
    }

}
