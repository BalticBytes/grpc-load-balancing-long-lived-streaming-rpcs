package loadgrpc;

import java.util.logging.Logger;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import loadgrpc.shared.Constants;

public class ClientIntercepter implements ClientInterceptor {

  protected static final Logger LOGGER = Logger.getLogger(ClientIntercepter.class.getName());
  public static CallOptions.Key<String> key = CallOptions.Key.create(Constants.keyName);

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
    return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        headers.put(Metadata.Key.of(Constants.keyName, Metadata.ASCII_STRING_MARSHALLER), callOptions.getOption(key));
        super.start(responseListener, headers);
      }
    };
  }

}
