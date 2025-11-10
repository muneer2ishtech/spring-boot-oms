# springboot-oms
Order Management system using Spring Boot Rest APIs


## Tech stack
- Java - 25
- spring-boot - 3.5.7
- MySQL / MariaDB
- Docker
- Flyway

## Design
### Assumptions:
1. No multi-tenancy
1. No VATs (or other taxes)
    1. In case of discount at order level, they need to be divided at item level so that VAT amount becomes proportionate.
1. No Inventory tracking for orders
1. No Units for quantity (e.g. g / kg etc)
1. Only Integer quantities in order
1. No Audit tables

### Some more design considerations
1. Separate customer table is not created, treating users as customer. Can be improved by having t_customer and information of billing address, shipping address etc
1. Apply best possible discounts vs apply all discounts
    1. Coding is done for both cases
    1. But testing is done only for apply best possible discount

1. Why unitPrice again in `SalesOrderItem` when it is already available in `Product`?
    1. It gives flexibility to update product price
    1. Historical prices you can get from audit table
    1. Doesn't affect past `SalesOrder(s)`
    1. Price change in `Product` does not impact pending orders
    1. For pending `SalesOrder(s)`, it gives flexibility to decide whether to keep old price or update to new price

1. Why name of `SalesOder` and not just `Order`
    1. To avoid keywords
    1. Also you may need to differentiate between sales orders and purchase orders
    1. Separate tables is better even though you can achieve it by having single table and a column to flag them

1. Using DB migrations e.g. flyway (or alternatives like liquibase) are better, as you can keep your code changes and DB changes in sync.
     1. `spring.jpa.hibernate.ddl-auto=create-drop` should never be used in production DB
     1. `spring.jpa.hibernate.ddl-auto=update` can keep up with entity class changes, but can cause irrevocable loss to data, e.g. if entity attribute is removed or class itself is removed by mistake it will drop columns or tables.


### Some Code considerations
1. You can avoid explicit `@Table(name = "t_...")` by extending `CamelCaseToUnderscoresNamingStrategy` and setting `spring.jpa.hibernate.naming.physical-strategy` in `application.properties`

1. If you are having both column value and FK relation in the entity class then, you must mention `name` in `@Column`, else you will get error like

```
Table [t_sales_order] contains physical column name [customer_id] referred to by multiple logical column names: [customer_id], [customerId]
```

1. FK names, Unique constraint names, enum definitions are given explicitly in entity classes, so that they also can work with so that it can work smoothly with `spring.jpa.hibernate.ddl-auto` with values of `create`, `create-drop`, `update`


## APIs

- See [CURL-INFO.md](./CURL-INFO.md) for sample RestAPI requests
- See API Docs [swagger-ui.html](http://localhost:8080/swagger-ui.html) (App should be running)


## DB

### Local
- You need local instance or docker of local MariaDB / MySQL
    - To run using MySQL instead of MariaDB, comment out MariaDB portions and uncomment MySQL portions in `pom.xml` and `application-xxx.properties`

- I have customized docker for various databases
    - For MariaDB
        - See [https://github.com/IshTech/docker-mysql/tree/main/mariadb](https://github.com/IshTech/docker-mysql/tree/main/mariadb)
    - For MySQL
        - See [https://github.com/IshTech/docker-mysql/tree/main/mysql](https://github.com/IshTech/docker-mysql/tree/main/mysql)
- Login to DB as root and run [init_db.sql](src/test/resources/db/init_db.sql) to setup DB Schema, DB User and Grant privileges

#### DB Access

```
mariadb -u istech_oms_dev_user -pishtech_oms_dev_pass -D ishtech_oms_dev_db
```
or

```
mysql -u istech_oms_dev_user -pishtech_oms_dev_pass -D ishtech_oms_dev_db
```

## Build and Run

- Ensure the port, db properties etc are correct in application-xxx.properties

### Local Maven Build

- Build without tests

```
./mvnw clean install -DskipTests
```

- Build with Junit tests

```
./mvnw clean install
```

- Run

```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Docker

#### Docker build

```
docker build . \
--build-arg SERVER_PORT=8080 \
-t "$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout):$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)"

```

### Run with docker compose

- Docker compose is self contained and has both spring-boot application and mariadb is present, so  you don't need anything else other than docker

- To stop if running

```
docker compose stop

```

- To build and start
    - You can prefix with env vars as in below example

```
SERVER_PORT=8383 DB_PORT=23306 APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout) \
docker-compose up --build

```
