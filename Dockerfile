FROM openjdk:21-jdk-slim

ARG APP_VERSION=0.2.0-SNAPSHOT
ARG SERVER_PORT=8080

ENV TZ=Europe/Helsinki

COPY target/spring-boot-oms-${APP_VERSION}.jar spring-boot-oms.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "/spring-boot-oms.jar"]
