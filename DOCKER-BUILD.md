## Docker

### Docker build

- arg for custom `SERVER_PORT` is optional, you can change to desired value or skip, if skipped it will use default `8080`

```
docker build . \
  --build-arg SERVER_PORT=8282 \
  -t "muneer2ishtech/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout 2>/dev/null):$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null)"

```

### Run with docker compose

- Docker compose is self contained and has both spring-boot application and db is present, so  you don't need anything else other than docker

- To stop if running
    - `docker compose stop`

- To stop and remove including volumes and built images
    - `docker compose down -v --rmi=local`

- To build and start
    - You can prefix with env vars as in below example
    - Below args are optional, you can change to desired value or skip, if skipped they will use default value
        - `DB_PORT` if skipped DB will be exposed on default `3306`
        - `SERVER_PORT_REMOTE` if skipped spring-boot app will run on default `8080`
        - `SERVER_PORT_LOCAL` if skipped spring-boot app will be exposed on default `8080`

```
SERVER_PORT_LOCAL=8282 \
DB_PORT=23306 \
APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
docker compose up --build

```
