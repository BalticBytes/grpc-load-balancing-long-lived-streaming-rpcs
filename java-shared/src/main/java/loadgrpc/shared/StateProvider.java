package loadgrpc.shared;

import java.util.logging.Logger;

public class StateProvider implements ThresholdAware {

  protected static final Logger LOGGER = Logger.getLogger(StateProvider.class.getName());
  private final String fmt = "[LoadManager] %1$-14s (%2$04d requests) (%3$04d connections)";
  private final String fmt1 = "[State] %s => %s";

  private static volatile StateProvider instance;
  private String state;
  
  private StateProvider() {
    Utils.setupLogging(LOGGER);
    this.state = "";
  }

  public static StateProvider getInstance() {
    StateProvider result = instance;
    if (result == null) {
      synchronized (StateProvider.class) {
        result = instance;
        if (result == null) {
          instance = result = new StateProvider();
        }
      }
    }
    return result;
  }
  
  public synchronized String getState() {
    var numRequests = RequestAggregator.getInstance().sum();
    var numConnections = ConnectionAggregator.getInstance().getCurrentConnections();
    var newState = this.getLoadString(numRequests);
    if(Constants.isDebugEnabled) LOGGER.info(String.format(fmt1, state, newState));
    this.setState(newState);
    LOGGER.info(String.format(fmt, newState, numRequests, numConnections));
    ReportingRunnable.getInstance().maybeShutdownServer(newState);
    return state;
  }

  public synchronized void setState(String state) {
    this.state = state;
  }
}