package loadgrpc;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.util.concurrent.MoreExecutors;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import loadgrpc.shared.CustomHealthServiceImpl;
import loadgrpc.shared.ReportingRunnable;
import loadgrpc.shared.Utils;

public class FlowServer {

    protected static final Logger LOGGER = Logger.getLogger(FlowServer.class.getName());

    public static void main(String[] args) {
        Utils.setupLogging(LOGGER);
        try {
        var port = 50051;
        var containerId = InetAddress.getLocalHost().getHostName();
        // disables maxConnAge
        var maxConnAge = Long.MAX_VALUE;
        var maxConnAgeGrace = Long.MAX_VALUE;

        LOGGER.info("[Server] Flow Control server start");
        LOGGER.info(String.format("[Server] Settings containerId(%1$s),maxConnAge(%2$s),maxConnAgeGrace(%3$s),myIP(%4$s)", containerId, maxConnAge, maxConnAgeGrace, InetAddress.getLocalHost().getHostAddress()));

        var service = new ExperimentService();
        var interceptor = new ExperimentInterceptor();
        var health = new CustomHealthServiceImpl();

        var executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        var executorService = MoreExecutors.getExitingExecutorService(executor, 1, TimeUnit.SECONDS);
        
        final Server server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .maxConnectionAge(Long.MAX_VALUE, TimeUnit.MINUTES)
                .addService(service)
                .addService(health)
                .intercept(interceptor)
                .executor(executorService)
                .keepAliveTimeout(1, TimeUnit.MINUTES)
                .maxConnectionIdle(1, TimeUnit.MINUTES)
                .build();

        ReportingRunnable.getInstance().setServer(server);
        executorService.submit(ReportingRunnable.getInstance());
        executorService.submit(FlowController.getInstance());
        
        server.awaitTermination();
        FlowController.getInstance().stop();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[Server] unexpected error", e);
        }
    }
}
