FROM openjdk:8-jre-alpine
EXPOSE 8084
WORKDIR /app
COPY target/file-manager-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "file-manager-0.0.1-SNAPSHOT.jar" ]
