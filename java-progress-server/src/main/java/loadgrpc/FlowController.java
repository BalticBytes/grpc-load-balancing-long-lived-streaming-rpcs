package loadgrpc;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import loadgrpc.shared.Constants;
import loadgrpc.shared.StateProvider;
import loadgrpc.shared.Utils;
import loadgrpc.shared.ConnectionAggregator;

public class FlowController implements Runnable {
  protected static final Logger LOGGER = Logger.getLogger(FlowController.class.getName());
  private static volatile FlowController instance;

  private ConcurrentLinkedQueue<String> suspended;
  private ConcurrentHashMap<String, CompletableFuture<String>> releaseMap;
  private CountDownLatch shutdownLatch;
  private boolean shouldStop;
  private long releaseInterval;

  private FlowController() {
    Utils.setupLogging(LOGGER);
    suspended = new ConcurrentLinkedQueue<>();
    shutdownLatch = new CountDownLatch(0);
    releaseMap = new ConcurrentHashMap<>();
    shouldStop = false;
    releaseInterval = Utils.readEnv("loadgrpc_server_release_interval_ms", 1000L);
  }

  public static FlowController getInstance() {
    FlowController result = instance;
    if (result == null) {
      synchronized (FlowController.class) {
        result = instance;
        if (result == null) {
          instance = result = new FlowController();
        }
      }
    }
    return result;
  }

  public synchronized CompletableFuture<String> suspend(String requestId) {
    var f = new CompletableFuture<String>();
    this.suspended.add(requestId);
    this.releaseMap.put(requestId, f);
    LOGGER.info(String.format("[FlowControl] suspend %s (%s)", requestId, this.suspended.size()));
    return f;
  }

  private synchronized void release(String requestId) {
    this.releaseMap.get(requestId).complete(requestId);
    LOGGER.info(String.format("[FlowControl] release %s (%s)", requestId, this.suspended.size()));
  }

  public void stop() {
    this.shouldStop = true;
    try {
      shutdownLatch.await(15L, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.log(Level.SEVERE, String.format("[FlowControl] err"), e);
    }
  }

  private void sleep(long i) {
    try {
      Thread.sleep(i);
    } catch (InterruptedException e) {
      LOGGER.log(Level.SEVERE, String.format("[FlowControl] err"), e);
    }
  }

  @Override
  public void run() {
    if (Constants.isDebugEnabled) LOGGER.info(String.format("[FlowControl] start"));
    while (!shouldStop) {
      if (!this.suspended.isEmpty()) {
        release(this.suspended.poll());
      }
      sleep(releaseInterval);
    }
    shutdownLatch.countDown();
    if (Constants.isDebugEnabled) LOGGER.info(String.format("[FlowControl] shutdown"));
  }
}
