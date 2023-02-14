package loadgrpc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import loadgrpc.shared.Utils;

import com.google.common.util.concurrent.Uninterruptibles;

import io.grpc.Status;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import loadgrpc.flowcontrol.FlowControlResponse;
import loadgrpc.flowcontrol.FlowControlledMsg;
import loadgrpc.shared.Constants;
import loadgrpc.shared.StateProvider;

public final class StreamObserverImpl implements StreamObserver<FlowControlledMsg> {

  protected static final Logger LOGGER = Logger.getLogger(StreamObserverImpl.class.getName());
  protected static boolean setup = false;
  
  private final ServerCallStreamObserver<FlowControlResponse> responseObserver;

  StreamObserverImpl(ServerCallStreamObserver<FlowControlResponse> responseObserver) {
    this.responseObserver = responseObserver;
    if (!setup) {
      Utils.setupLogging(LOGGER);
      setup = true;
    }
  }

  @Override
  public void onNext(FlowControlledMsg value) {
    // The server receives a message from the client

    var state = StateProvider.getInstance().getState();

    switch (state) {
      case Constants.strFAILURE:
        if(Constants.isDebugEnabled) LOGGER.warning("Aborting connection " + value.getRequestId() + " [" + state + "]");
        responseObserver.onError(Status.INTERNAL.withDescription("Aborting connection " + value.getRequestId() + " [" + state + "]").asException());
        break;
      case Constants.strOVERLOAD:
        CompletableFuture<String> future = FlowController.getInstance().suspend(value.getRequestId());
        future
        .thenAccept(id -> {
          if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection]     resume (%s)", id));
          responseObserver.onNext(FlowControlResponse.newBuilder().setRequest(1).build());
        })
        .exceptionally((t) -> {
          if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection] abort (%s) reason(%s)", value.getRequestId(), t.getMessage()));
          responseObserver.onError(t);
          return null;
        });
        responseObserver.onNext(FlowControlResponse.newBuilder().setRequest(0).build());
        break;
      case Constants.strHIGH:
      case Constants.strMED:
      case Constants.strLOW:
      default:
        if (!this.responseObserver.isCancelled()) {
          if(Constants.isDebugEnabled) LOGGER.info(String.format("[Connection]      reply (%s) %s %s", value.getRequestId(), state, StateProvider.getInstance().getState()));
          responseObserver.onNext(FlowControlResponse.newBuilder().setRequest(1).build());
        } else {
          responseObserver.onError(Status.INTERNAL.withDescription("[Server] Client isCancelled").asException());
        }
        break;
    }
  }

  @Override
  public void onError(Throwable t) {
    
  }

  @Override
  public void onCompleted() {
    responseObserver.onCompleted();
  }
}