# MonitorDemo for Applifting

## How to start the app

Unless you specify otherwise, the app will start at http://localhost:8080

### Spring-boot run
One option is to run a database locally and start the application using spring-boot:run maven goal. Before running the app put database related configuration to environment variables.

```bash
export SPRING_DATASOURCE_USERNAME=applifting  #set according to your database
export SPRING_DATASOURCE_PASSWORD=password  #set according to your database
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/endpointMonitoring?createDatabaseIfNotExist=true #set according to your database
export SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect
export SPRING_JPA_HIBERNATE_DDL_AUTO=create-drop
mvn spring-boot:run
```

### Building and starting docker image
Alternatively you can run it by building the application and docker image that you can run. Again set database related configuration according to your database.
```bash
mvn clean install
docker build . -t applifting
docker run --network host -e SPRING_DATASOURCE_USERNAME=applifting -e SPRING_DATASOURCE_PASSWORD=password -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/endpointMonitoring?createDatabaseIfNotExist=true -e SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect -e SPRING_JPA_HIBERNATE_DDL_AUTO=create-drop  applifting
```

### Running the application using docker compose
You can start a database and the app by using docker compose. By sure that nothing is running on port 3306 otherwise the database will not start.

```bash
docker-compose up
```

##Using tha application

### Creating user

#### Users
##### POST /users
Will create a new user. You do not need to send any body, but if you want you can send user details in format: {"username":"some username", "email":"some@email.com"}
You will receive the token of the user.

#### Monitored endpoints

Put the token as a value of a header Authorization in format "custom <your token>".

#### POST /endpoint-monitors
Will create a new endpoint.  "name" is optional "url" and "monitoringIntervalInSeconds" are required.
Link to the created endpoint is in the response in the header "Content-Location"

#### GET /endpoint-monitors}
Will return all monitored endpoints of the user authenticated by the token.

#### GET /endpoint-monitors/{id}

Will return info about the monitored endpoint

#### GET /endpoint-monitors/{id}/results/top
Will return top last 10 monitoring results


#### PUT /endpoint-monitors/{id}
Will update the monitoring endpoint. You can update "name", "url", and "monitoringIntervalInSeconds"

#### DELETE /endpoint-monitors/{id}
Will delete the endpoint


## Notes:

This state is not finished state. Before creating merge request to any shared branch more tests should be done. Current state of tests is not satisfying, I have created just an example of tests.

