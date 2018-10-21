# IoT Web Server - PoC #
Web server to access refrigerator inventory over the web.

This web application uses [Spring Data REST](https://docs.spring.io/spring-data/rest/docs/current/reference/html/) 
project to create [HAL](https://tools.ietf.org/html/draft-kelly-json-hal-08)/[HATEOAS](https://restfulapi.net/hateoas/) 
compliant API.

## TODO ##

1. __Re-evaluate Spring Data REST__ - This is a good toolset for quick prototyping, but the opinionated
framework requires too many concessions based on the evaluation during this PoC.  Main issues:  
    * ID conflict during de-serialization. https://github.com/spring-projects/spring-hateoas/issues/67  
    * javax.validation at controller level. Appears to be rejected by the project since they want a separation between
    entities and DTO objects. https://github.com/spring-projects/spring-boot/issues/6574  
    * Nested entities issues, which could be configuration. However, the general purpose of this project   
    is to separate entity interactions into multiple calls. With the size of the expected payloads efficiency will 
    be gained by reducing the number of calls.
2. __Generated API Documentation__ - As a micro service, this will need to produce easily understood documentation 
for developers.

## Development ##
Start the application by executing the `bootRun` task for this project.

### Local Database ###
The local database is setup using the [gradle postgresql embedded plugin](https://github.com/honourednihilist/gradle-postgresql-embedded).

The connection properties are set in the `gradle.properties` file with the prefix `db.`.

### Database Migration ###
Database migration is done using [flywayDB](https://flywaydb.org).

Flyway scripts MUST be placed in `src/main/resources/db/migration` according to the [flyway version migration standards](https://flywaydb.org/documentation/migrations#versioned-migrations).

When executing the application locally, the [flyway gradle plugin](https://flywaydb.org/documentation/gradle/) will execute the 
`flywayMigrate` command to update the database according to the supplied scripts.

#### Hibernate DDL Creation ###
To assist the creation of SQL scripts to use in flyway, there is a gradle task that will 
create DDL scripts from annotated classes.

    gradlew generateSchema -PdatabaseProductName={DB NAME}

The `databaseProductName` property is optional and is defaulted to `PostgreSQL`. 
Additional values are listed [on the plugin's website](https://github.com/divinespear/jpa-schema-gradle-plugin#for-hibernate).

This will generate version and undo files using the current version number.

These scripts are not production quality, and MUST be manually edited as needed.

## Testing ##
Currently, testing for this project is only integration testing.  Meaning that tests 
will execute HTTP calls against the server. 

When the `test` command is executed the following tasks will be run to setup dependencies.

* `flywayMigrate` - setup database for application
* `bootJar` - create spring boot jar
* `testServer` - executes the spring boot jar in a separate process