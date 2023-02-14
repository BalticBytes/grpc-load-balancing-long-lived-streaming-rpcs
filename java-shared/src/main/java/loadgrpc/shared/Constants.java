package loadgrpc.shared;

public class Constants {
  
  public static final String strFAILURE = "SYSTEM FAILURE";
  public static final String strOVERLOAD = "OVERLOAD";
  public static final String strHIGH = "HIGH LOAD";
  public static final String strMED = "MEDIUM LOAD";
  public static final String strLOW = "LOW LOAD";

  public static final String keyName = "custom-request-id";
  public static final boolean isDebugEnabled = !Utils.readEnv("loadgrpc_debug", "").isBlank();
}
