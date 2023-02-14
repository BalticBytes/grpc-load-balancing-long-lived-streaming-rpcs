package loadgrpc;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.LongStream;

import io.grpc.Status;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import loadgrpc.experiment.BaselineMsg;
import loadgrpc.experiment.EmptyMsg;
import loadgrpc.shared.Utils;

public class ExperimentObserver implements ClientResponseObserver<BaselineMsg, EmptyMsg>, Runnable {

  private static final Logger LOGGER = Logger.getLogger(ExperimentObserver.class.getName());

  private ClientCallStreamObserver<BaselineMsg> requestStream;

  private String requestId;
  private String containerId;
  private CountDownLatch latch;
  private long[] msgIntervals;
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
    var numMessages = Utils.readEnv("loadgrpc_client_num_messages", 10);
    var mVariance = Utils.readEnv("loadgrpc_client_variance_message", 0);
    msgIntervals = Utils.interval(duration, numMessages, mVariance, 50);
    
    LOGGER.info(String.format("[Client] interval[%s]%s", requestId, Arrays.toString(msgIntervals)));
    LOGGER.info(String.format("[Client] start(%s) until %s & %s", requestId, duration, LongStream.of(msgIntervals).sum()));
    return latch;
  }

  @Override
  public void beforeStart(ClientCallStreamObserver<BaselineMsg> requestStream) {
    this.requestStream = requestStream;
  }

  @Override
  public void onNext(EmptyMsg value) {
    // Server only sends final message
  }

  @Override
  public void onError(Throwable t) {
    Status status = Status.fromThrowable(t);
    onError.accept(status);
    latch.countDown();
  }

  @Override
  public void onCompleted() {
    onSuccess.accept(index);
    latch.countDown();
  }

  @Override
  public void run() {
    for (; index.get() < msgIntervals.length; index.getAndIncrement()) {
      var t = this.msgIntervals[index.get()];
      try {
        Thread.sleep(t);
      } catch (InterruptedException e) {
      }
      LOGGER.info(String.format("[Client] send(%s):part(%s/%s) slept %s", requestId, 1 + index.get(), msgIntervals.length, t));
      this.requestStream.onNext(BaselineMsg.newBuilder().setRequestId(requestId).setSenderHostname(containerId).build());
    }
    this.requestStream.onCompleted();
  }
}
