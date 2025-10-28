# spring-boot-oms
Order Management system using Spring Boot Rest APIs

## Run without build

### Docker
- Docker compose of both spring-boot application and mariadb is present
- You don't need to install or build anything, just docker running on your machine is enough

- To start in foreground (from root of this project)

```
docker compose up

```
- To start in background

```
docker compose up -d

```

- To stop

```
docker compose stop

```


## Assumptions:
1. No Multi tenancy
1. No VATs (or other taxes)
    1. In case of discount at order level, they need to be devided at item level so that VAT amount becomes proportionate.
1. No Inventory tracking for orders
1. No Units for quantity (e.g. g / kg etc)
1. Only Integer quantities in order
1. No Audit tables

## Some Design points
1. Separate customer table is not created, treating users as customer. Can be improved by having t_customer and information of billing address, shipping address etc
1. Apply best possible discounts vs apply all discounts
    1. Coding is done for both cases
    1. But testing is done only for apply best possible discount
1. Why unitPrice again in SalesOrderItem when you can get from Product and use?
    1. It gives flexibility to update product price
    1. Historical prices you can get from audit table
    1. Doesn't affect past SalesOrders
    1. For pending SalesOrders, it gives flexibility to decide whether to keep old price or update to new price
    1. unitPrice change in Product does not impact pending orders

1. Why name of SalesOder and not just Order
    1. To avoid keywords
    1. Also you may need to differentiate between sales orders and purchase orders
    1. Separate tables is better even though you can achieve it by having single table and a column to flag them

1. Using DB migrations e.g. flyway etc. are better, as you can keep your code changes and DB changes in sync.
     1. `spring.jpa.hibernate.ddl-auto=create-drop` should never be used in production DB
     2. `spring.jpa.hibernate.ddl-auto=update` can keep up with entity class changes, but can cause irrevocable loss to data, e.g. if entity attribute is removed or class itself is removed by mistake it will drop columns or tables.


## Some Code points
1. You can avoid explicit `@Table(name = "t_...")` by extending `CamelCaseToUnderscoresNamingStrategy` and setting `spring.jpa.hibernate.naming.physical-strategy` in `application.properties`

1. If you are having both column value and FK relation in the entity class then, you must mention `name` in `@Column`, else you will get error like

```
Table [t_sales_order] contains physical column name [customer_id] referred to by multiple logical column names: [customer_id], [customerId]
```

1. FK names, Unique constraint names, enum definitions are given explicitly in entity classes, so that they also can work with so that it can work smoothly with `spring.jpa.hibernate.ddl-auto` with values of `create`, `create-drop`, `update`

## Build and Run

### Local
- Need
    - JDK21 or higher
    - Maven
    - MariaDB or Docker

- DB
    - Start mariadb
    - See below for docker of MariaDB
    - Login to DB as root and run [init_db.sql](src/main/resources/db/init_db.sql) to DB Schema, DB User and Grant privileges
- Build

```
mvn clean install
```

- Run
    - Ensure the port, db properties are correct in application.properties

```
mvn spring-boot:run
```

### Docker

```
docker build --progress=plain --no-cache -t "$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout):$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)" -f build.Dockerfile .

```

```
docker-compose -f local-docker-compose.yml up --build

```

### Test

- See [CURLS.md](curls.md) for sample RestAPI requests
- See API Docs [swagger-ui.html](http://localhost:8080/swagger-ui.html) (App should be running)


## DB

### Local
- I have customized docker for MariaDB to use
- See [https://github.com/IshTech/docker-mysql/tree/main/mariadb](https://github.com/IshTech/docker-mysql/tree/main/mariadb)

### DB Access

```
mariadb -u istech_oms_dev_user -pishtech_oms_dev_pass -D ishtech_oms_dev_db
```
