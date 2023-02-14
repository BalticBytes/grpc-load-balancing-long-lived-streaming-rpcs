FROM maven:3.8.6-openjdk-18-slim as build

COPY ./java-shared /workspaces/java-shared
WORKDIR /workspaces/java-shared
RUN mvn clean install -DskipTests

COPY ./java-normal-client /workspaces/java-normal-client
WORKDIR /workspaces/java-normal-client
RUN mvn clean package -DskipTests

FROM eclipse-temurin:19-jre-ubi9-minimal

WORKDIR /workspaces
COPY ./wait-for-it.sh /workspaces/wait-for-it.sh
RUN chmod +x /workspaces/wait-for-it.sh

WORKDIR /workspaces/java-normal-client/target
COPY --from=build /workspaces/java-normal-client/target/client-0.3-java-shaded.jar /workspaces/java-normal-client/target/client.jar

CMD /workspaces/wait-for-it.sh $loadgrpc_server_hostname --quiet --timeout=60 -- java -jar client.jar