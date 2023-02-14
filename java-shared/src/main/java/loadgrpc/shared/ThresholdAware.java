package loadgrpc.shared;

public interface ThresholdAware {

  final static int[] thresholds = new int[]{
    Utils.readEnv("loadgrpc_server_threshold_failure", 150),
    Utils.readEnv("loadgrpc_server_threshold_overload", 100),
    Utils.readEnv("loadgrpc_server_threshold_high", 75),
    Utils.readEnv("loadgrpc_server_threshold_medium", 50)
  };
  
  public default String getLoadString(long numRequests) {
    if (thresholds[0] < numRequests) {
      return Constants.strFAILURE;
    } else if (thresholds[1] < numRequests) {
      return Constants.strOVERLOAD;
    } else if (thresholds[2] < numRequests) {
      return Constants.strHIGH;
    } else if (thresholds[3] < numRequests) {
      return Constants.strMED;
    } else {
      return Constants.strLOW;
    }
  }
}
