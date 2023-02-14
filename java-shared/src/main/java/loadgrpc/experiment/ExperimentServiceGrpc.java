package loadgrpc.experiment;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: experiments.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ExperimentServiceGrpc {

  private ExperimentServiceGrpc() {}

  public static final String SERVICE_NAME = "loadgrpc.ExperimentService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<loadgrpc.experiment.BaselineMsg,
      loadgrpc.experiment.EmptyMsg> getClientStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ClientStreaming",
      requestType = loadgrpc.experiment.BaselineMsg.class,
      responseType = loadgrpc.experiment.EmptyMsg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<loadgrpc.experiment.BaselineMsg,
      loadgrpc.experiment.EmptyMsg> getClientStreamingMethod() {
    io.grpc.MethodDescriptor<loadgrpc.experiment.BaselineMsg, loadgrpc.experiment.EmptyMsg> getClientStreamingMethod;
    if ((getClientStreamingMethod = ExperimentServiceGrpc.getClientStreamingMethod) == null) {
      synchronized (ExperimentServiceGrpc.class) {
        if ((getClientStreamingMethod = ExperimentServiceGrpc.getClientStreamingMethod) == null) {
          ExperimentServiceGrpc.getClientStreamingMethod = getClientStreamingMethod =
              io.grpc.MethodDescriptor.<loadgrpc.experiment.BaselineMsg, loadgrpc.experiment.EmptyMsg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ClientStreaming"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.experiment.BaselineMsg.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.experiment.EmptyMsg.getDefaultInstance()))
              .setSchemaDescriptor(new ExperimentServiceMethodDescriptorSupplier("ClientStreaming"))
              .build();
        }
      }
    }
    return getClientStreamingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExperimentServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExperimentServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExperimentServiceStub>() {
        @java.lang.Override
        public ExperimentServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExperimentServiceStub(channel, callOptions);
        }
      };
    return ExperimentServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExperimentServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExperimentServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExperimentServiceBlockingStub>() {
        @java.lang.Override
        public ExperimentServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExperimentServiceBlockingStub(channel, callOptions);
        }
      };
    return ExperimentServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExperimentServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExperimentServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExperimentServiceFutureStub>() {
        @java.lang.Override
        public ExperimentServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExperimentServiceFutureStub(channel, callOptions);
        }
      };
    return ExperimentServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ExperimentServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.experiment.BaselineMsg> clientStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.experiment.EmptyMsg> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getClientStreamingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getClientStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                loadgrpc.experiment.BaselineMsg,
                loadgrpc.experiment.EmptyMsg>(
                  this, METHODID_CLIENT_STREAMING)))
          .build();
    }
  }

  /**
   */
  public static final class ExperimentServiceStub extends io.grpc.stub.AbstractAsyncStub<ExperimentServiceStub> {
    private ExperimentServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExperimentServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExperimentServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.experiment.BaselineMsg> clientStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.experiment.EmptyMsg> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getClientStreamingMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class ExperimentServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ExperimentServiceBlockingStub> {
    private ExperimentServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExperimentServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExperimentServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class ExperimentServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ExperimentServiceFutureStub> {
    private ExperimentServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExperimentServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExperimentServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_CLIENT_STREAMING = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExperimentServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExperimentServiceImplBase serviceImpl, int methodId) {
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
        case METHODID_CLIENT_STREAMING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.clientStreaming(
              (io.grpc.stub.StreamObserver<loadgrpc.experiment.EmptyMsg>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ExperimentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ExperimentServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return loadgrpc.experiment.Experiments.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ExperimentService");
    }
  }

  private static final class ExperimentServiceFileDescriptorSupplier
      extends ExperimentServiceBaseDescriptorSupplier {
    ExperimentServiceFileDescriptorSupplier() {}
  }

  private static final class ExperimentServiceMethodDescriptorSupplier
      extends ExperimentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ExperimentServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ExperimentServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExperimentServiceFileDescriptorSupplier())
              .addMethod(getClientStreamingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
