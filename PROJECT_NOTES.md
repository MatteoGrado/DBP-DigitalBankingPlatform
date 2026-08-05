# Project Notes

## Scope

- Spring Boot service: `User-Service`
- Java: `21`
- Parent: `org.springframework.boot:spring-boot-starter-parent:4.0.7`
- Main package: `de.grado.userservice`

## Runtime Topology

- The project is containerized with Compose.
- Relevant Compose services:
  - `user-service`
  - `user-db`
  - `consul`
- Inside the Compose network, `user-service` must reach:
  - MySQL at `user-db:3306`
  - Consul at `consul:8500`

## Environment Variables

- Database credentials are expected from `.env`:
  - `MYSQL_USER_DATABASE`
  - `MYSQL_USER_USER`
  - `MYSQL_USER_PWD`
  - `MYSQL_ROOT_PASSWORD`
- Avoid using `localhost` for container-to-container communication.

## Important Configuration

- Main app config is in `User-Service/src/main/resources/application.yaml`
- Test overrides belong in `User-Service/src/test/resources/application.yaml`
- Build behavior can be controlled with `-Dskiptest`

## Build and Run

- Local build:
  - `mvn clean package -Dskiptest`
- Container build:
  - `docker compose build user-service`
- Compose startup:
  - `docker compose up -d`

## Known Constraints

- `@SpringBootTest` will start the full application context.
- Tests must not depend on external infrastructure unless that infrastructure is explicitly available.
- Consul discovery should be disabled in tests unless a test Consul instance is running.

## Working Notes

- Prefer updating Compose-aware hostnames in config rather than hardcoding `localhost`.
- Keep build-time and test-time configuration separate from runtime container config.
