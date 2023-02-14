package loadgrpc;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.primitives.Longs;
import com.google.common.util.concurrent.AtomicLongMap;
import com.google.common.util.concurrent.MoreExecutors;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.Status.Code;
import loadgrpc.experiment.ExperimentServiceGrpc;
import loadgrpc.shared.Utils;

public class MyClient {

    protected static final Logger LOGGER = Logger.getLogger(MyClient.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Utils.setupLogging(LOGGER);
        var timeout = 1000L * Utils.readEnv("loadgrpc_client_initial_delay_s", 1);
        var host = Utils.readEnv("loadgrpc_server_hostname", "127.0.0.1:50051");

        var durationMs = 1000L * Utils.readEnv("loadgrpc_client_request_time_s", 5);
        var numRequests = Utils.readEnv("loadgrpc_client_num_requests", 6);
        var rVariance = Utils.readEnv("loadgrpc_client_variance_request", 0);
        var numMessages = Utils.readEnv("loadgrpc_client_num_messages", 10);
        var mVariance = Utils.readEnv("loadgrpc_client_variance_message", 0);

        LOGGER.info(String.format("[Client] variance request=%s & message=%s", rVariance, mVariance));

        var containerId = InetAddress.getLocalHost().getHostName();
        var workload = new ArrayDeque<String>(numRequests);
        var finished = new ArrayDeque<String>(numRequests);
        var workloadInterval = new HashMap<String, Long>(numRequests);
        var requestIntervals = Utils.interval(durationMs * numRequests, numRequests, rVariance, 50);
        
        Thread.sleep(timeout);
        for (int i = 0; i < numRequests; i++) {
            var requestId = String.format("1-%s-%s", containerId, i);
            workload.add(requestId);
            workloadInterval.put(requestId, requestIntervals[i]);
        }
        LOGGER.log(Level.INFO, "[Client] requestIds = " + Arrays.toString(workload.toArray()));
        Thread.sleep(Utils.rng.nextLong(timeout));
        LOGGER.log(Level.INFO, "[Client] reqIntervals = " + Arrays.toString(requestIntervals));
        
        var executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        var executorService = MoreExecutors.getExitingExecutorService(executor, 1, TimeUnit.SECONDS);

        var intercepter = new ClientIntercepter();
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget(host)
                .disableRetry()
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .executor(executorService)
                .intercept(intercepter)
                .build();
        
        var responseObserver = new ExperimentObserver(containerId);
        var retryMap = AtomicLongMap.<String>create();
        var shouldStop = new AtomicBoolean(false);
        while (!shouldStop.getAcquire() && !channel.isTerminated() && !workload.isEmpty()) {
            var requestId = workload.pop();
            try {
                var interval = workloadInterval.get(requestId);
                var latch = responseObserver.prepare(
                    requestId, interval,
                    (index) -> {
                        LOGGER.log(Level.INFO, String.format("[Client] recv %s %s", requestId, Status.Code.OK));
                        finished.add(requestId);
                    },
                    (status) -> {
                        LOGGER.log(Level.INFO, String.format("[Client] recv %s %s", requestId, status.getCode()));
                        if (status.getCode() == Code.UNAVAILABLE) {
                            shouldStop.set(true);
                        }
                        if (retryMap.incrementAndGet(requestId) <= 2L) {
                            workload.add(requestId);
                            LOGGER.log(Level.INFO, String.format("[Client] retry %s", requestId));
                        }
                });
            
            var stub = ExperimentServiceGrpc.newStub(channel)
                .withOption(ClientIntercepter.key, requestId)
                .withWaitForReady()
                .withDeadlineAfter(2 * interval, TimeUnit.MILLISECONDS)
                .withExecutor(executorService);

            stub.clientStreaming(responseObserver);
            
            executor.submit(responseObserver);
            latch.await(15, TimeUnit.MINUTES);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "[Server] unexpected error", e);
                shouldStop.set(true);
                if (retryMap.incrementAndGet(requestId) <= 2L) {
                    workload.add(requestId);
                    LOGGER.log(Level.INFO, String.format("[Client] retry %s", requestId));
                }
            }
        }
        channel.shutdown();
        channel.awaitTermination(15, TimeUnit.SECONDS);
        LOGGER.info(String.format("[Client] Work done finished(%s) unfinished(%s) retries(%s)", finished.size(), workload.size(), retryMap.sum()));
    }

}
