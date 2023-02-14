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

public class MyServer {

    protected static final Logger LOGGER = Logger.getLogger(MyServer.class.getName());

    public static void main(String[] args) {
        Utils.setupLogging(LOGGER);
        try {
        var port = 50051;
        var containerId = InetAddress.getLocalHost().getHostName();
        var maxConnAge = Utils.readEnv("loadgrpc_client_request_time_s", 0L);
        var maxConnAgeGrace = Utils.readEnv("loadgrpc_server_max_grace_conn_age_ms", 0L);
        if (maxConnAgeGrace == 0L) {
            // disables maxConnAge
            LOGGER.info("[Server] Normal server start");
            LOGGER.info(String.format("[Server] Settings containerId(%1$s),maxConnAge(%2$s),maxConnAgeGrace(%3$s),myIP(%4$s)", containerId, maxConnAge, maxConnAgeGrace, InetAddress.getLocalHost().getHostAddress()));
            maxConnAge = Long.MAX_VALUE;
            maxConnAgeGrace = Long.MAX_VALUE;
        } else {
            LOGGER.info("[Server] Disconnect server start");
            LOGGER.info(String.format("[Server] Settings containerId(%1$s),maxConnAge(%2$s),maxConnAgeGrace(%3$s),myIP(%4$s)", containerId, maxConnAge, maxConnAgeGrace, InetAddress.getLocalHost().getHostAddress()));
        }
        var service = new ExperimentService();
        var interceptor = new ExperimentInterceptor();
        var health = new CustomHealthServiceImpl();

        var executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        var executorService = MoreExecutors.getExitingExecutorService(executor, 1, TimeUnit.SECONDS);

        final Server server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .maxConnectionAge(maxConnAge, TimeUnit.SECONDS)
                .maxConnectionAgeGrace(maxConnAgeGrace, TimeUnit.MILLISECONDS)
                .addService(service)
                .addService(health)
                .intercept(interceptor)
                .executor(executorService)
                .keepAliveTimeout(1, TimeUnit.MINUTES)
                .maxConnectionIdle(1, TimeUnit.MINUTES)
                .build();
        
        ReportingRunnable.getInstance().setServer(server);
        executorService.submit(ReportingRunnable.getInstance());

        server.awaitTermination();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[Server] unexpected error", e);
        }
    }
}
