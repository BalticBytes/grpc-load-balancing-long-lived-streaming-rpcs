package loadgrpc.flowcontrol;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: improvement.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FlowControlServiceGrpc {

  private FlowControlServiceGrpc() {}

  public static final String SERVICE_NAME = "loadgrpc.FlowControlService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<loadgrpc.flowcontrol.FlowControlledMsg,
      loadgrpc.flowcontrol.FlowControlResponse> getControlledStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ControlledStreaming",
      requestType = loadgrpc.flowcontrol.FlowControlledMsg.class,
      responseType = loadgrpc.flowcontrol.FlowControlResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<loadgrpc.flowcontrol.FlowControlledMsg,
      loadgrpc.flowcontrol.FlowControlResponse> getControlledStreamingMethod() {
    io.grpc.MethodDescriptor<loadgrpc.flowcontrol.FlowControlledMsg, loadgrpc.flowcontrol.FlowControlResponse> getControlledStreamingMethod;
    if ((getControlledStreamingMethod = FlowControlServiceGrpc.getControlledStreamingMethod) == null) {
      synchronized (FlowControlServiceGrpc.class) {
        if ((getControlledStreamingMethod = FlowControlServiceGrpc.getControlledStreamingMethod) == null) {
          FlowControlServiceGrpc.getControlledStreamingMethod = getControlledStreamingMethod =
              io.grpc.MethodDescriptor.<loadgrpc.flowcontrol.FlowControlledMsg, loadgrpc.flowcontrol.FlowControlResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ControlledStreaming"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.flowcontrol.FlowControlledMsg.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.flowcontrol.FlowControlResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FlowControlServiceMethodDescriptorSupplier("ControlledStreaming"))
              .build();
        }
      }
    }
    return getControlledStreamingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FlowControlServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FlowControlServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FlowControlServiceStub>() {
        @java.lang.Override
        public FlowControlServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FlowControlServiceStub(channel, callOptions);
        }
      };
    return FlowControlServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FlowControlServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FlowControlServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FlowControlServiceBlockingStub>() {
        @java.lang.Override
        public FlowControlServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FlowControlServiceBlockingStub(channel, callOptions);
        }
      };
    return FlowControlServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FlowControlServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FlowControlServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FlowControlServiceFutureStub>() {
        @java.lang.Override
        public FlowControlServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FlowControlServiceFutureStub(channel, callOptions);
        }
      };
    return FlowControlServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FlowControlServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.flowcontrol.FlowControlledMsg> controlledStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.flowcontrol.FlowControlResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getControlledStreamingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getControlledStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                loadgrpc.flowcontrol.FlowControlledMsg,
                loadgrpc.flowcontrol.FlowControlResponse>(
                  this, METHODID_CONTROLLED_STREAMING)))
          .build();
    }
  }

  /**
   */
  public static final class FlowControlServiceStub extends io.grpc.stub.AbstractAsyncStub<FlowControlServiceStub> {
    private FlowControlServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlowControlServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FlowControlServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.flowcontrol.FlowControlledMsg> controlledStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.flowcontrol.FlowControlResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getControlledStreamingMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class FlowControlServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<FlowControlServiceBlockingStub> {
    private FlowControlServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlowControlServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FlowControlServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class FlowControlServiceFutureStub extends io.grpc.stub.AbstractFutureStub<FlowControlServiceFutureStub> {
    private FlowControlServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlowControlServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FlowControlServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_CONTROLLED_STREAMING = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FlowControlServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FlowControlServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONTROLLED_STREAMING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.controlledStreaming(
              (io.grpc.stub.StreamObserver<loadgrpc.flowcontrol.FlowControlResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FlowControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FlowControlServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return loadgrpc.flowcontrol.Improvement.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FlowControlService");
    }
  }

  private static final class FlowControlServiceFileDescriptorSupplier
      extends FlowControlServiceBaseDescriptorSupplier {
    FlowControlServiceFileDescriptorSupplier() {}
  }

  private static final class FlowControlServiceMethodDescriptorSupplier
      extends FlowControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FlowControlServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FlowControlServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FlowControlServiceFileDescriptorSupplier())
              .addMethod(getControlledStreamingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
