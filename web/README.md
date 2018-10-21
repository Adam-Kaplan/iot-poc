# Smart Refrigerator - WebApp #
Web server to access refrigerator inventory over the web.

## API ##
This web application uses [Spring Data REST](https://docs.spring.io/spring-data/rest/docs/current/reference/html/) 
project to create [HAL](https://tools.ietf.org/html/draft-kelly-json-hal-08) compliant API.

## Development ##
Gradle command to start application locally

    gradlew clean bootRun

Gradle command to execute tests

    gradlew clean test
    
This task will execute the following tasks to execute integration tests.

* flywayMigrate - setup database for application
* bootJar - create spring boot jar
* testServer - executes the spring boot jar in a separate process

### Local Database ###
The local database is setup using the [gradle postgresql embedded plugin](https://github.com/honourednihilist/gradle-postgresql-embedded).

The connection properties are set in the `gradle.properties` file with the prefix `db.`.

### Database Migration ###
Database migration is done using [flywayDB](https://flywaydb.org).

Flyway scripts MUST be placed in `src/main/resources/db/migration` according to the [flyway version migration standards](https://flywaydb.org/documentation/migrations#versioned-migrations).

When executing the applicaiton locally, the [flyway gradle plugin](https://flywaydb.org/documentation/gradle/) will exeucte the 
`flywayMigrate` command to update the database according to the supplied scripts.

#### Hibernate DDL Creation ###
To assist the creation of SQL scripts to use in flyway, there is a gradle task that will 
create DDL scripts from annotated classes.

    gradlew generateSchema -PdatabaseProductName={DB NAME}

The `databaseProductName` property is optional and is defaulted to `PostgreSQL`. 
Additional values are listed [on the plugin's website](https://github.com/divinespear/jpa-schema-gradle-plugin#for-hibernate).

This will generate version and undo files using the current version number.

These scripts are not production quality, and MUST be manually edited as needed.

## TODO ##
1. Validation - JSR-380 validation at controller level.  Spring Data REST does not implement this ...
2. Message response localization.
3. Security - Annotations to use (@EnableWebSecurity, @EnableResourceServer). Will need to be addressed once security model 
is finalized.
