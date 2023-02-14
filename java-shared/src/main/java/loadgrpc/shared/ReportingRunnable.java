package loadgrpc.shared;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.Server;

public class ReportingRunnable implements Runnable {

  protected static final Logger LOGGER = Logger.getLogger(ReportingRunnable.class.getName());
  private static volatile ReportingRunnable instance;

  private Server server;

  private long maxIdleTime = 0L;
  private long currentIdleTime = 0L;
  private long reportingInterval = 0L;

  public ReportingRunnable() {
    Utils.setupLogging(LOGGER);
    this.reportingInterval = (long) Utils.readEnv("loadgrpc_server_sensing_interval_ms", 1000);
    this.maxIdleTime = (long) Utils.readEnv("loadgrpc_server_max_idle_time_ms", 15000);
    this.currentIdleTime = 0L;
  }

  public static ReportingRunnable getInstance() {
    ReportingRunnable result = instance;
    if (result == null) {
      synchronized (ReportingRunnable.class) {
        result = instance;
        if (result == null) {
          instance = result = new ReportingRunnable();
        }
      }
    }
    return result;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  private boolean isServerInFailingState(String state) {
    switch (state) {
      case Constants.strFAILURE:
        return true;
      case Constants.strOVERLOAD:
      case Constants.strHIGH:
      case Constants.strMED:
      case Constants.strLOW:
      default:
        return false;
    }
  }

  private boolean isServerIdle(int numRequests, long numConnections) {
    if (0 < numRequests) {
      this.currentIdleTime = 0;
    } else {
      this.currentIdleTime += this.reportingInterval;
    }
    return this.maxIdleTime <= this.currentIdleTime;
  }

  private void startServer() throws IOException {
    server.start();
    LOGGER.info(String.format("[Server] start on %s", server.getListenSockets().toString()));
  }

  public void shutdownServer() {
    if (this.maxIdleTime <= this.currentIdleTime) {
      LOGGER.info(String.format("[Server] shutdown (%s)ms inactivity", this.currentIdleTime));
    } else {
      LOGGER.log(Level.SEVERE, "[Server] unexpected shutdown");
    }
    server.shutdownNow();
  }

  private void waitUntilFirstConnection(long maxWaitTime) throws InterruptedException {
    var localWaitTime = 0L;
    while (
      ConnectionAggregator.getInstance().getCurrentConnections() == 0 &&
      localWaitTime < maxWaitTime
    ) {
      // Wait until first request comes
      Thread.sleep(this.reportingInterval);
      localWaitTime += this.reportingInterval;
    }
  }

  @Override
  public void run() {
    try {
      startServer();
      waitUntilFirstConnection(5 * 60 * 1000L);
      var state = "";
      var numRequests = 0;
      var numConnections = 0L;
      
      while (!isServerIdle(numRequests, numConnections)) {
        numRequests = RequestAggregator.getInstance().sum();
        numConnections = ConnectionAggregator.getInstance().getCurrentConnections();
        state = StateProvider.getInstance().getState();
        
        maybeShutdownServer(state);
        
        Thread.sleep(this.reportingInterval);
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "[Server] unexpected error", e);
    } finally {
      shutdownServer();
    }
  }

  public void maybeShutdownServer(String state) {
    if (isServerInFailingState(state)) {
      shutdownServer();
    }
  }
}
