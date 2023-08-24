FROM  openjdk:17-jdk-slim-buster
VOLUME /tmp
EXPOSE 8080
ADD ./target/crud-0.0.1-SNAPSHOT.jar crud.jar
ENTRYPOINT ["java", "-jar", "/crud.jar"]