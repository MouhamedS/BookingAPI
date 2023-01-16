# Reservation API
Kata Booking API with Hexagonale Architecture
Project build with JAVA 17

## Build
./mvnw clean install

## Launch
./mvnw spring-boot:run

## Swagger
http://localhost:8081/swagger-ui/index.html

##  API-DOCS
http://localhost:8081/api-docs/

## API YAML FILE

http://localhost:8081/api-docs.yaml

### Known Issues
The data.sql can't populate the database.
So To have the right environment to test this application
 - open the H2 console at http://localhost:8081/h2-console/
 - Get the username from the log at the start af the application et enter it 
 - execute the request from data.sql to the database