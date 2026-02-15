# TASK API

## Description

REST API to provide CRUD operations on Task objects

## API Endpoint documentation

On startup, see http://localhost:8080/swagger-ui/index.html

## Resources

See src/main/resources for:
- DB connect properties
- DB create scripts
- Postman collection
- OpenApi JSON

## Persistence

Connection details to a SQL Server database instance are given in src/main/resources/application.properties

## Tests

Unit Tests and Integration Tests can be found under src/test/java

## Start API Service

From Intellij:
- mvn clean package
- mvn sprint-boot:run

Alternatively, build within Intellij, click the green Run button

In future, add gradle tasks