package loadgrpc.example;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: example.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ExampleServiceGrpc {

  private ExampleServiceGrpc() {}

  public static final String SERVICE_NAME = "loadgrpc.ExampleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getUnaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Unary",
      requestType = loadgrpc.example.ExampleMessage.class,
      responseType = loadgrpc.example.ExampleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getUnaryMethod() {
    io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse> getUnaryMethod;
    if ((getUnaryMethod = ExampleServiceGrpc.getUnaryMethod) == null) {
      synchronized (ExampleServiceGrpc.class) {
        if ((getUnaryMethod = ExampleServiceGrpc.getUnaryMethod) == null) {
          ExampleServiceGrpc.getUnaryMethod = getUnaryMethod =
              io.grpc.MethodDescriptor.<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Unary"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ExampleServiceMethodDescriptorSupplier("Unary"))
              .build();
        }
      }
    }
    return getUnaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getClientStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ClientStreaming",
      requestType = loadgrpc.example.ExampleMessage.class,
      responseType = loadgrpc.example.ExampleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getClientStreamingMethod() {
    io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse> getClientStreamingMethod;
    if ((getClientStreamingMethod = ExampleServiceGrpc.getClientStreamingMethod) == null) {
      synchronized (ExampleServiceGrpc.class) {
        if ((getClientStreamingMethod = ExampleServiceGrpc.getClientStreamingMethod) == null) {
          ExampleServiceGrpc.getClientStreamingMethod = getClientStreamingMethod =
              io.grpc.MethodDescriptor.<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ClientStreaming"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ExampleServiceMethodDescriptorSupplier("ClientStreaming"))
              .build();
        }
      }
    }
    return getClientStreamingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getServerStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ServerStreaming",
      requestType = loadgrpc.example.ExampleMessage.class,
      responseType = loadgrpc.example.ExampleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getServerStreamingMethod() {
    io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse> getServerStreamingMethod;
    if ((getServerStreamingMethod = ExampleServiceGrpc.getServerStreamingMethod) == null) {
      synchronized (ExampleServiceGrpc.class) {
        if ((getServerStreamingMethod = ExampleServiceGrpc.getServerStreamingMethod) == null) {
          ExampleServiceGrpc.getServerStreamingMethod = getServerStreamingMethod =
              io.grpc.MethodDescriptor.<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ServerStreaming"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ExampleServiceMethodDescriptorSupplier("ServerStreaming"))
              .build();
        }
      }
    }
    return getServerStreamingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getBidirectionalStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BidirectionalStreaming",
      requestType = loadgrpc.example.ExampleMessage.class,
      responseType = loadgrpc.example.ExampleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage,
      loadgrpc.example.ExampleResponse> getBidirectionalStreamingMethod() {
    io.grpc.MethodDescriptor<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse> getBidirectionalStreamingMethod;
    if ((getBidirectionalStreamingMethod = ExampleServiceGrpc.getBidirectionalStreamingMethod) == null) {
      synchronized (ExampleServiceGrpc.class) {
        if ((getBidirectionalStreamingMethod = ExampleServiceGrpc.getBidirectionalStreamingMethod) == null) {
          ExampleServiceGrpc.getBidirectionalStreamingMethod = getBidirectionalStreamingMethod =
              io.grpc.MethodDescriptor.<loadgrpc.example.ExampleMessage, loadgrpc.example.ExampleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BidirectionalStreaming"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  loadgrpc.example.ExampleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ExampleServiceMethodDescriptorSupplier("BidirectionalStreaming"))
              .build();
        }
      }
    }
    return getBidirectionalStreamingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExampleServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExampleServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExampleServiceStub>() {
        @java.lang.Override
        public ExampleServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExampleServiceStub(channel, callOptions);
        }
      };
    return ExampleServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExampleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExampleServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExampleServiceBlockingStub>() {
        @java.lang.Override
        public ExampleServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExampleServiceBlockingStub(channel, callOptions);
        }
      };
    return ExampleServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExampleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExampleServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExampleServiceFutureStub>() {
        @java.lang.Override
        public ExampleServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExampleServiceFutureStub(channel, callOptions);
        }
      };
    return ExampleServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ExampleServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void unary(loadgrpc.example.ExampleMessage request,
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnaryMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.example.ExampleMessage> clientStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getClientStreamingMethod(), responseObserver);
    }

    /**
     */
    public void serverStreaming(loadgrpc.example.ExampleMessage request,
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getServerStreamingMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.example.ExampleMessage> bidirectionalStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getBidirectionalStreamingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUnaryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                loadgrpc.example.ExampleMessage,
                loadgrpc.example.ExampleResponse>(
                  this, METHODID_UNARY)))
          .addMethod(
            getClientStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                loadgrpc.example.ExampleMessage,
                loadgrpc.example.ExampleResponse>(
                  this, METHODID_CLIENT_STREAMING)))
          .addMethod(
            getServerStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                loadgrpc.example.ExampleMessage,
                loadgrpc.example.ExampleResponse>(
                  this, METHODID_SERVER_STREAMING)))
          .addMethod(
            getBidirectionalStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                loadgrpc.example.ExampleMessage,
                loadgrpc.example.ExampleResponse>(
                  this, METHODID_BIDIRECTIONAL_STREAMING)))
          .build();
    }
  }

  /**
   */
  public static final class ExampleServiceStub extends io.grpc.stub.AbstractAsyncStub<ExampleServiceStub> {
    private ExampleServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExampleServiceStub(channel, callOptions);
    }

    /**
     */
    public void unary(loadgrpc.example.ExampleMessage request,
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.example.ExampleMessage> clientStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getClientStreamingMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void serverStreaming(loadgrpc.example.ExampleMessage request,
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getServerStreamingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<loadgrpc.example.ExampleMessage> bidirectionalStreaming(
        io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getBidirectionalStreamingMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class ExampleServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ExampleServiceBlockingStub> {
    private ExampleServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExampleServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public loadgrpc.example.ExampleResponse unary(loadgrpc.example.ExampleMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnaryMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<loadgrpc.example.ExampleResponse> serverStreaming(
        loadgrpc.example.ExampleMessage request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getServerStreamingMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ExampleServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ExampleServiceFutureStub> {
    private ExampleServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExampleServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<loadgrpc.example.ExampleResponse> unary(
        loadgrpc.example.ExampleMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnaryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UNARY = 0;
  private static final int METHODID_SERVER_STREAMING = 1;
  private static final int METHODID_CLIENT_STREAMING = 2;
  private static final int METHODID_BIDIRECTIONAL_STREAMING = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExampleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExampleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UNARY:
          serviceImpl.unary((loadgrpc.example.ExampleMessage) request,
              (io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse>) responseObserver);
          break;
        case METHODID_SERVER_STREAMING:
          serviceImpl.serverStreaming((loadgrpc.example.ExampleMessage) request,
              (io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse>) responseObserver);
          break;
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
              (io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse>) responseObserver);
        case METHODID_BIDIRECTIONAL_STREAMING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.bidirectionalStreaming(
              (io.grpc.stub.StreamObserver<loadgrpc.example.ExampleResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ExampleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ExampleServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return loadgrpc.example.Example.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ExampleService");
    }
  }

  private static final class ExampleServiceFileDescriptorSupplier
      extends ExampleServiceBaseDescriptorSupplier {
    ExampleServiceFileDescriptorSupplier() {}
  }

  private static final class ExampleServiceMethodDescriptorSupplier
      extends ExampleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ExampleServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ExampleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExampleServiceFileDescriptorSupplier())
              .addMethod(getUnaryMethod())
              .addMethod(getClientStreamingMethod())
              .addMethod(getServerStreamingMethod())
              .addMethod(getBidirectionalStreamingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
