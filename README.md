## Technical Exercise

### How-To run the project:
1. Run a Maven deployment: mvnw clean install
1. Run the apllication: java -jar target/ActionMonitor-1.0.0.jar

### Notes during implementation:
1. The project was initialized via: https://start.spring.io/
1. Code formatting is based on Google Java style: https://github.com/google/styleguide
1. Database: H2 is used right now as an embedded database
1. Spring Integration tests were wrtitten for the API
1. Project Lombok was used for the Java POJO classes: https://projectlombok.org/
1. IntelliJ IDEA was used for the development
1. POSTMAN collection is available under: /src/main/resources directory

### Notes for the future:
1. Using Spring HATEOS for every answer not only for error handling
1. Using Swagger for the API representation: https://swagger.io/
1. Using OpenAPI for the documentation of the API: https://github.com/OAI/OpenAPI-Specification