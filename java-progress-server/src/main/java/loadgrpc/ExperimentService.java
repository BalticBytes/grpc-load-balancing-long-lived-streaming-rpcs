package loadgrpc;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import loadgrpc.flowcontrol.FlowControlledMsg;
import loadgrpc.flowcontrol.FlowControlServiceGrpc.FlowControlServiceImplBase;
import loadgrpc.flowcontrol.FlowControlResponse;

public class ExperimentService extends FlowControlServiceImplBase {

  @Override
  public StreamObserver<FlowControlledMsg> controlledStreaming(StreamObserver<FlowControlResponse> responseObserver) {
    var serverCallStreamObserver = (ServerCallStreamObserver<FlowControlResponse>) responseObserver;
    return new StreamObserverImpl(serverCallStreamObserver);
  }
}
