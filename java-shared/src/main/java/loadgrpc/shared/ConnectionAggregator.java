package loadgrpc.shared;

import java.util.logging.Logger;

import com.google.common.primitives.Longs;
import com.google.common.util.concurrent.AtomicLongMap;

public class ConnectionAggregator {

  protected static final Logger LOGGER = Logger.getLogger(ConnectionAggregator.class.getName());

  private static volatile ConnectionAggregator instance;

  private AtomicLongMap<String> connections;
  private AtomicLongMap<String> suspended;

  private ConnectionAggregator() {
    this.connections = AtomicLongMap.create();
    this.suspended = AtomicLongMap.create();
    Utils.setupLogging(LOGGER);
  }

  public synchronized void connection(String reqId) {
    this.connections.incrementAndGet(reqId);
    if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection]    connect (%s) ", reqId));
  }

  public synchronized void disconnection(String reqId, String reason) {
    if (reqId == null) return;
    this.connections.decrementAndGet(reqId);
    this.connections.removeIfZero(reqId);
    if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection] disconnect (%s) reason(%s)", reqId, reason));
  }

  public synchronized void disconnection(String reqId) {
    this.connections.decrementAndGet(reqId);
    this.connections.removeIfZero(reqId);
    if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection] disconnect (%s)", reqId));
  }

  public synchronized void suspend(String reqId) {
    if (!this.isSuspended(reqId)) {
      var x = this.suspended.incrementAndGet(reqId);
      if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection]    suspend (%s) %d", reqId, x));
    } else {
      if(Constants.isDebugEnabled) LOGGER.warning(String.format("[Connection]    suspend (%s) again", reqId));
    }
  }

  public synchronized void release(String reqId) {
    if (this.isSuspended(reqId)) {
      var x = this.suspended.decrementAndGet(reqId);
      this.suspended.removeIfZero(reqId);
      if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection]    release (%s) %d", reqId, x));
    } else {
      if(Constants.isDebugEnabled) LOGGER.warning(String.format("[Connection]    release (%s) again", reqId));
    }
  }

  public boolean isSuspended(String reqId) {
    return Longs.compare(this.suspended.get(reqId), 1L) == 0;
  }

  public synchronized long getCurrentConnections() {
    return this.connections.sum();
  }

  public static ConnectionAggregator getInstance() {
    ConnectionAggregator result = instance;
    if (result == null) {
      synchronized (ConnectionAggregator.class) {
        result = instance;
        if (result == null) {
          instance = result = new ConnectionAggregator();
        }
      }
    }
    return result;
  }
}
