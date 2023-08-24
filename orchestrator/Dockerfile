FROM  openjdk:17-jdk-slim-buster
VOLUME /tmp
EXPOSE 9090
ADD ./target/orchestrator-0.0.1-SNAPSHOT.jar orchestrator.jar
ENTRYPOINT ["java", "-jar", "/orchestrator.jar"]