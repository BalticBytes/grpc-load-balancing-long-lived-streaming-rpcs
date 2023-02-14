package loadgrpc;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.LongStream;

import io.grpc.Context;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import loadgrpc.flowcontrol.FlowControlResponse;
import loadgrpc.flowcontrol.FlowControlledMsg;
import loadgrpc.shared.Utils;

public class ExperimentObserver implements ClientResponseObserver<FlowControlledMsg, FlowControlResponse>, Runnable {

  private static final Logger LOGGER = Logger.getLogger(ExperimentObserver.class.getName());

  private ClientCallStreamObserver<FlowControlledMsg> requestStream;

  private String requestId;
  private String containerId;
  private CountDownLatch latch;
  private long[] msgIntervals;
  private AtomicBoolean stopped = new AtomicBoolean(false);
  private AtomicBoolean sent = new AtomicBoolean(false);
  private AtomicBoolean completed = new AtomicBoolean(false);
  private AtomicInteger index = new AtomicInteger(0);

  private Consumer<AtomicInteger> onSuccess;
  private Consumer<Status> onError;

  public ExperimentObserver(String containerId) {
    Utils.setupLogging(LOGGER);
    this.containerId = containerId;
  }

  public CountDownLatch prepare(
      String requestId, Long duration,
      Consumer<AtomicInteger> onSuccess,
      Consumer<Status> onError) {
    this.onSuccess = onSuccess;
    this.onError = onError;
    this.latch = new CountDownLatch(1);
    this.requestId = requestId;
    this.index = new AtomicInteger(0);
    this.stopped = new AtomicBoolean(false);
    this.completed = new AtomicBoolean(false);
    this.sent = new AtomicBoolean(false);
    var numMessages = Utils.readEnv("loadgrpc_client_num_messages", 10);
    var mVariance = Utils.readEnv("loadgrpc_client_variance_message", 0);
    msgIntervals = Utils.interval(duration, numMessages, mVariance, 50);
    
    LOGGER.info(String.format("[Client] interval[%s]%s", requestId, Arrays.toString(msgIntervals)));
    LOGGER.info(String.format("[Client] start(%s) until %s & %s", requestId, duration, LongStream.of(msgIntervals).sum()));
    return latch;
  }

  public synchronized void send(int n) {
    var start = Math.min(index.get() + n, msgIntervals.length);
    for (; index.get() < start && !stopped.get(); index.getAndIncrement()) {
      var t = this.msgIntervals[index.get()];
      sleep(t);
      LOGGER.info(String.format("[Client] send(%s):part(%s/%s) slept %s", requestId, 1 + index.get(), msgIntervals.length, t));
      this.requestStream.onNext(
          FlowControlledMsg.newBuilder()
              .setRequestId(requestId)
              .setSenderHostname(containerId)
              .build());
    }
  }

  public void sleep(long t) {
    try {
      Thread.sleep(t);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public synchronized void stop(String reason) {
    if (!stopped.get()) {
      LOGGER.info(String.format("[Client] stop (%s) %s", requestId, reason));
      stopped.set(true);
    }
  }

  @Override
  public void beforeStart(ClientCallStreamObserver<FlowControlledMsg> requestStream) {
    this.requestStream = requestStream;
    this.requestStream.setOnReadyHandler(this);
  }

  @Override
  public void onNext(FlowControlResponse value) {
    if (index.get() >= msgIntervals.length && value.getRequest() == 1) {
      // stop("[client is done sending] " + index.get());
      LOGGER.info(String.format("[Client] end(%s)", requestId));
      if (!completed.get()) {
        this.requestStream.onCompleted();
        completed.set(true);
      }
    } else {
      var n = value.getRequest();
      if (n == 0) {
        // LOGGER.info("[Client] server responds with request{0}");
        // stop("[server responds with request{0}]");
      } else {
        // LOGGER.info("[Client] Send 1");
        send(n);
      }
    }
  }

  @Override
  public void onError(Throwable t) {
    // Response handling
    Status status = Status.fromThrowable(t);
    stop(String.format("[server err %s]", status));
    onError.accept(status);
    latch.countDown();
  }

  @Override
  public void onCompleted() {
    // Response handling
    // LOGGER.warning(String.format("[Client] onCompleted"));
    stop("[client finished rpc]");
    onSuccess.accept(index);
    latch.countDown();
  }

  @Override
  public void run() {
    if (requestStream.isReady() && !sent.get() && !stopped.get()) {
      // LOGGER.info("requestStream.isReady");
      send(1);
      sent.set(true);
    }
  }
}
