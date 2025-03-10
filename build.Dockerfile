# Build with Maven
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY . .

ARG MAVEN_CLI_OPTS="-B -q"

RUN ./mvnw $MAVEN_CLI_OPTS clean package -DskipTests

# Run as a Spring Boot application
FROM eclipse-temurin:21-jre-alpine

ARG APP_VERSION=0.2.0-SNAPSHOT
ARG SERVER_PORT=8080
ARG TZ=Europe/Helsinki

LABEL APP_NAME=spring-boot-oms
LABEL PROJECT_VERSION=$PROJECT_VERSION

ENV TZ=$TZ

WORKDIR /app

COPY --from=build app/target/spring-boot-oms-${APP_VERSION}.jar spring-boot-oms.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "spring-boot-oms.jar"]
