package loadgrpc.shared;

import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.health.v1.HealthGrpc.HealthImplBase;
import io.grpc.health.v1.HealthCheckResponse.ServingStatus;

public class CustomHealthServiceImpl extends HealthImplBase implements ThresholdAware, IService {

  protected static final Logger LOGGER = Logger.getLogger(CustomHealthServiceImpl.class.getName());
  public String currentStatus = "";

  public CustomHealthServiceImpl() {
    Utils.setupLogging(LOGGER);
  }

  public String getCurrentStatus() {
    synchronized (this) {
      return currentStatus;
    }
  }

  public void setCurrentState(String currentStatus) {
    this.currentStatus = currentStatus;
  }

  @Override
  public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
    var status = ServingStatus.SERVING;
    var state = getCurrentStatus();
    LOGGER.info("[Health] check " + status + " because " + state);
    switch (state) {
      case Constants.strFAILURE:
      case Constants.strOVERLOAD:
      status = ServingStatus.NOT_SERVING;
      break;
      case Constants.strHIGH:
      case Constants.strMED:
      case Constants.strLOW:
      default:
        status = ServingStatus.SERVING;
        break;
    }
    var response = HealthCheckResponse.newBuilder().setStatus(status).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void watch(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
    var status = ServingStatus.SERVING;
    var state = getCurrentStatus();
    LOGGER.info("[Health] watch " + status + " because " + state);
    var response = HealthCheckResponse.newBuilder().setStatus(status).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
