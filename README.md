# Budgetfy

Budgetfy is a budget tracking application that helps manage finances by recording income, expenses, and savings in one place, and providing insights and reports. It is built using various technologies and tools, including:

## Technologies

- Java
- Spring
- PostgreSQL
- Flyway
- JSON Web Tokens (JWT)

## Tools

- Lombok
- MapStruct
- SpringDoc OpenAPI

## Dependencies

The project uses the following dependencies:

- `spring-boot-starter-security` for authentication and authorization
- `spring-security-test` for testing security
- `spring-boot-starter-data-jpa` for working with databases using the Java Persistence API (JPA)
- `spring-boot-starter-validation` for data validation
- `spring-boot-starter-web` for building web applications
- `spring-boot-starter-test` for testing
- `postgresql` as the JDBC driver for PostgreSQL
- `flyway-core` for database migrations
- `modelmapper` for mapping between objects
- `mapstruct` for generating type-safe mapping code at compile time
- `lombok` for reducing boilerplate code
- `springdoc-openapi-starter-webmvc-ui` for generating OpenAPI documentation
- `jjwt-api`, `jjwt-impl`, and `jjwt-jackson` for JSON Web Tokens (JWT) support

## Usage

To run the application, execute the following command in the root directory of the project:
```
mvn clean instal
```
```
mvn spring-boot:run
```


After running the application, navigate to http://localhost:8080/swagger-ui/index.html to access the Swagger UI and interact with the API.

## Contributing

Contributions to the project are welcome. To contribute, follow these steps:

1. Fork the project
2. Create a new branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -m "Add my feature"`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
