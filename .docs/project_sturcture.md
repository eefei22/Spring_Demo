```text
src/main/java/
`-- com/example/demo/
    |-- DemoApplication.java                 → Spring Boot entry point (includes a CommandLineRunner DB connection check)
    |-- component/                           → Helper components
    |   |-- TextFormatter.java               → Formats greeting output text
    |   `-- TimeComponent.java               → Time-based greeting logic (morning/afternoon/evening)
    |-- controller/                          → HTTP controllers
    |   |-- AuthController.java              → Login endpoint(s) under /auth
    |   |-- GreetingController.java          → Greeting demo endpoints (/greet, /greet-no-di, /greet-config, /greetuser)
    |   `-- JournalController.java           → User-scoped journal CRUD + search endpoints
    |-- dto/                                 → Request/response payloads
    |   |-- JournalRequest.java              → Create/update journal request body
    |   |-- JournalResponse.java             → Journal response body
    |   |-- LoginRequest.java                → Login request body
    |   `-- LoginResponse.java               → Login response body
    |-- entity/                              → JPA entities
    |   |-- JournalEntry.java                → Journal record
    |   `-- User.java                        → Application user
    |-- exception/                           → API exception types + error response mapping
    |   |-- BadRequestException.java         → 400-style input validation error
    |   |-- ResourceNotFoundException.java   → 404-style missing resource error
    |   |-- ErrorResponse.java               → Standard error response body
    |   `-- GlobalExceptionHandler.java      → @RestControllerAdvice mapping exceptions to HTTP responses
    |-- repository/                          → Spring Data JPA repositories
    |   |-- JournalRepository.java           → Journal persistence + custom queries
    |   `-- UserRepository.java              → User persistence + lookup by username
    `-- service/                             → Business logic layer
        |-- GreetingService.java             → Greeting logic (DI + config-based message)
        |-- GreetingService_0.java           → Greeting logic without DI
        |-- JournalService.java              → Journal operations (throws domain exceptions)
        `-- UserService.java                 → Login validation

```

