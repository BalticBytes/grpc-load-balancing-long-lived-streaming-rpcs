package loadgrpc.shared;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RequestAggregator implements ThresholdAware {
  protected static final Logger LOGGER = Logger.getLogger(RequestAggregator.class.getName());
  
  private static volatile RequestAggregator instance;
  private RealTimeSlidingWindow requestWindow;

  private RequestAggregator() {
    // this.messagesPerSecond = new ConcurrentLinkedQueue<>();
    Utils.setupLogging(LOGGER);
    var settingFmt = "[LoadManager] Settings failure(%1$d) > overload(%2$d) > high(%3$d) > medium(%4$d)";
    LOGGER.info(String.format(settingFmt, thresholds[0], thresholds[1], thresholds[2], thresholds[3]));
    this.requestWindow = new RealTimeSlidingWindow(1000L, TimeUnit.MILLISECONDS);
  }

  public static RequestAggregator getInstance() {
    RequestAggregator result = instance;
    if (result == null) {
      synchronized (RequestAggregator.class) {
        result = instance;
        if (result == null) {
          instance = result = new RequestAggregator();
        }
      }
    }
    return result;
  }
  
  public void request() {
    this.requestWindow.add(1);
  }

  public synchronized int sum() {
    this.requestWindow.add(0);
    return this.requestWindow.sum();
  }
}
