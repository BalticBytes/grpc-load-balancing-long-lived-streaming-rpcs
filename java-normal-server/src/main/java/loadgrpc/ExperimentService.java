package loadgrpc;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import loadgrpc.experiment.BaselineMsg;
import loadgrpc.experiment.EmptyMsg;
import loadgrpc.experiment.ExperimentServiceGrpc.ExperimentServiceImplBase;

public class ExperimentService extends ExperimentServiceImplBase {

  @Override
  public StreamObserver<BaselineMsg> clientStreaming(StreamObserver<EmptyMsg> responseObserver) {
    var serverCallStreamObserver = (ServerCallStreamObserver<EmptyMsg>) responseObserver;
    return new StreamObserverImpl(serverCallStreamObserver);
  }
}
