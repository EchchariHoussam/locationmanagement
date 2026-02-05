# CarMarket Pro

Architecture microservices **production-ready** pour une plateforme d’achat et de location de voitures avec prédiction intelligente des prix.

## Stack technique

- **Java 21**, **Spring Boot 3.3+**
- **Maven** (multi-module)
- **REST API** + **Swagger OpenAPI** (springdoc-openapi)
- **PostgreSQL** (un schéma/DB par microservice)
- **Flyway** pour les migrations SQL
- **Validation** : Jakarta Validation (`@Valid`)
- **DTO** + **MapStruct**
- **Logs structurés**, **X-Correlation-Id** propagé entre services
- **Gestion d’erreurs** : `@RestControllerAdvice`
- **Sécurité** : Spring Security + **JWT** (Access + Refresh) + **RBAC** (USER, ADMIN, AGENCY)
- **Dockerfile** par service + **docker-compose.dev**
- **Tests** : JUnit 5 + **Testcontainers**
- **Observabilité** : Actuator (health, metrics)

## Microservices

| Service | Port | Description |
|-------|------|------------|
| discovery-service | 8761 | Eureka Server |
| api-gateway | 8080 | Spring Cloud Gateway |
| config-service | 8888 | Config Server |
| auth-service | 8081 | Auth + Users |
| catalog-service | 8082 | Catalogue voitures |
| rental-service | 8083 | Réservations |
| transaction-service |
