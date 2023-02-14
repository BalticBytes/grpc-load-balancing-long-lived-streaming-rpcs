FROM maven:3.8.6-openjdk-18-slim as build

COPY ./java-shared /workspaces/java-shared
WORKDIR /workspaces/java-shared
RUN mvn clean install -DskipTests

COPY ./java-normal-server /workspaces/java-normal-server
WORKDIR /workspaces/java-normal-server
RUN mvn clean package -DskipTests

FROM eclipse-temurin:19-jre-ubi9-minimal

RUN GRPC_HEALTH_PROBE_VERSION=v0.4.13 && \
    wget -qO/bin/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64 && \
    chmod +x /bin/grpc_health_probe

WORKDIR /workspaces/java-normal-server/target
COPY --from=build /workspaces/java-normal-server/target/server-0.3-java-shaded.jar /workspaces/java-normal-server/target/server.jar

# CMD java -jar server.jar
ENTRYPOINT ["java", "-jar", "server.jar"]