package loadgrpc.shared;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class RealTimeSlidingWindow {
    private final long windowDuration;
    private final TimeUnit windowUnit;
    private final Deque<TimestampedValue> window = new LinkedList<>();

    public RealTimeSlidingWindow(long windowDuration, TimeUnit windowUnit) {
        this.windowDuration = windowDuration;
        this.windowUnit = windowUnit;
    }

    public void add(int value) {
        long timestamp = System.currentTimeMillis();
        TimestampedValue timestampedValue = new TimestampedValue(timestamp, value);
        synchronized (window) {
            window.addLast(timestampedValue);
            while (timestamp - window.peekFirst().timestamp > windowUnit.toMillis(windowDuration)) {
                window.removeFirst();
            }
        }
    }

    public int sum() {
        synchronized (window) {
            int sum = 0;
            for (TimestampedValue value : window) {
                sum += value.value;
            }
            return sum;
        }
    }

    @Override
    public String toString() {
        synchronized (window) {
            return window.toString();
        }
    }

    private static class TimestampedValue {
        long timestamp;
        int value;

        TimestampedValue(long timestamp, int value) {
            this.timestamp = timestamp;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + this.value + "," + this.timestamp + ")";
        }
    }
}