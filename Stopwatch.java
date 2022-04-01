public class Stopwatch {
    private boolean running;
    private long start;
    private long end;

    //Constructor
    public Stopwatch() {
        this.start = 0;
        this.end = 0;
    }

    //Determines if running
    public boolean isRunning() {
        return running;
    }

    //Starts stopwatch
    public void start() {
        start = System.nanoTime();
        running = true;
    }

    //Stops stopwatch
    public long stop() {
        if (!isRunning()) {
            return -1;
        } else {
            end = System.nanoTime();
            running = false;
            return end - start;
        }
    }

    //returns time elapsed
    public long elapsed() {
        if (isRunning()) {
            return (System.nanoTime() - start);
        } else
            return (end - start);
    }

    //returns a string in milliseconds
    public String toString() {
        long enlapsed = elapsed();
        return ((double) enlapsed / 1000000.0) + " Milliseconds";
    }

}
