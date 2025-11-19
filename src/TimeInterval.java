import java.time.Instant;
import java.time.Duration;
public class TimeInterval {
    private Instant startTime;
    private Instant endTime;

    public Instant start() {
        this.startTime = Instant.now();
        this.endTime = null; // Reset end time if starting again
        return startTime;
    }

    public Instant stop() {
        if (this.startTime == null) {
            System.out.println("Timer not started yet.");
        }
        this.endTime = Instant.now();
        return endTime;
    }

    public Duration getElapsedTime() {
        if (this.startTime == null || this.endTime == null) {
            return null; // Or throw an exception
        }
        return Duration.between(this.startTime, this.endTime);
    }



}
