package loadgrpc;

import java.util.logging.Logger;

import loadgrpc.shared.Utils;

import io.grpc.Status;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import loadgrpc.experiment.BaselineMsg;
import loadgrpc.experiment.EmptyMsg;
import loadgrpc.shared.Constants;
import loadgrpc.shared.StateProvider;

public final class StreamObserverImpl implements StreamObserver<BaselineMsg> {

  protected static final Logger LOGGER = Logger.getLogger(StreamObserverImpl.class.getName());
  protected static boolean setup = false;
  
  private final ServerCallStreamObserver<EmptyMsg> responseObserver;

  StreamObserverImpl(ServerCallStreamObserver<EmptyMsg> responseObserver) {
    this.responseObserver = responseObserver;
    if (!setup) {
      Utils.setupLogging(LOGGER);
      setup = true;
    }
  }

  @Override
  public void onNext(BaselineMsg value) {
    // The server receives a message from the client

    var state = StateProvider.getInstance().getState();

    // Handle Load
    switch (state) {
      case Constants.strFAILURE:
        if(Constants.isDebugEnabled) LOGGER.warning("Aborting connection " + value.getRequestId() + " [" + state + "]");
        responseObserver.onError(Status.INTERNAL.withDescription(state).asException());
        break;
      case Constants.strOVERLOAD:
      case Constants.strHIGH:
      case Constants.strMED:
      case Constants.strLOW:
      default:
        break;
    }
  }

  @Override
  public void onError(Throwable t) {
    
  }

  @Override
  public void onCompleted() {
    if (!responseObserver.isCancelled()){
      EmptyMsg reply = EmptyMsg.newBuilder().build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }
}