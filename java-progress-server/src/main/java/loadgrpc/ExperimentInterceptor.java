package loadgrpc;

import java.util.logging.Logger;

import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import loadgrpc.flowcontrol.FlowControlledMsg;
import loadgrpc.shared.ConnectionAggregator;
import loadgrpc.shared.Constants;
import loadgrpc.shared.RequestAggregator;
import loadgrpc.shared.StateProvider;
import loadgrpc.shared.Utils;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;

public final class ExperimentInterceptor implements ServerInterceptor {

    protected static final Logger LOGGER = Logger.getLogger(ExperimentInterceptor.class.getName());

    private final class DoNothing<ReqT> extends ServerCall.Listener<ReqT> {
    }

    private final Key<String> key = Metadata.Key.of(Constants.keyName, Metadata.ASCII_STRING_MARSHALLER);

    public ExperimentInterceptor() {
        Utils.setupLogging(LOGGER);
    }

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            final Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        var requestId = headers.get(this.key);
        var state = StateProvider.getInstance().getState();
        switch (state) {
            case Constants.strFAILURE:
                if(Constants.isDebugEnabled) LOGGER.info(String.format("[Server] (%s) reject call because {%s}", requestId, state));
                call.close(Status.INTERNAL, new Metadata());
                return new DoNothing<ReqT>(); // NOOP
            case Constants.strOVERLOAD:
            case Constants.strHIGH:
            case Constants.strMED:
            case Constants.strLOW:
            default:
                // A request from a client
                if (requestId != null) {
                    ConnectionAggregator.getInstance().connection(requestId);
                }

                return new SimpleForwardingServerCallListener<ReqT>(next.startCall(call, headers)) {
                    @Override 
                    public void onMessage(ReqT request) {
                        if (request instanceof FlowControlledMsg) {
                            RequestAggregator.getInstance().request();
                        }
                        super.onMessage(request);
                    }

                    @Override 
                    public void onCancel() {
                        if (requestId != null) {
                            ConnectionAggregator.getInstance().disconnection(requestId, String.format("onCancel %s %s", requestId, StateProvider.getInstance().getState()));
                        }
                        super.onCancel();
                    }

                    @Override 
                    public void onComplete() {
                        if (requestId != null) {
                            ConnectionAggregator.getInstance().disconnection(requestId, String.format("onComplete %s %s", requestId, StateProvider.getInstance().getState()));
                        }
                        super.onComplete();
                    }
                };
        }
    }
}