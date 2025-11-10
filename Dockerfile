# ====== Stage 1: Build ======
FROM eclipse-temurin:25-jdk AS build

COPY . .

ARG MAVEN_CLI_OPTS="-B -q"

RUN ./mvnw $MAVEN_CLI_OPTS clean install -DskipTests=true

# ====== Stage 2: Runtime ======
FROM eclipse-temurin:25-jre

ARG SERVER_PORT=8080
ARG TZ=Europe/Helsinki

ENV TZ=$TZ

EXPOSE ${SERVER_PORT:-8080}

COPY --from=build target/ishtech-springboot-oms-*.jar ishtech-springboot-oms.jar

ENTRYPOINT ["java", "-jar", "ishtech-springboot-oms.jar"]
