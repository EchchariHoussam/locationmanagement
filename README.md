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
- **Tests** : JUnit 5 + **Testcontainers** (smoke test par service)
- **Observabilité** : Actuator (health, metrics)

## Microservices

| Service | Port | Description |
|--------|------|-------------|
| discovery-service | 8761 | Eureka Server (service discovery) |
| api-gateway | 8080 | Spring Cloud Gateway |
| config-service | 8888 | Spring Cloud Config Server |
| auth-service | 8081 | Auth + Users (JWT, RBAC) |
| catalog-service | 8082 | Catalogue voitures |
| rental-service | 8083 | Réservation location |
| transaction-service | 8084 | Achat / transactions |
| payment-service | 8085 | Paiement |
| notification-service | 8086 | Notifications |
| analytics-service | 8087 | Analytics / reporting |
| prediction-service | 8088 | Prédiction ML (prix) |

## Format de réponse API standard

Toutes les API renvoient le format suivant :

```json
{
  "success": true,
  "message": "string",
  "data": { ... },
  "errors": [],
  "meta": { "page": 1, "size": 10, "total": 0 }
}
```

- **Pagination** : `page`, `size`
- **Tri** : `sort`
- **Filtres** : query params selon le service

## Prérequis

- JDK 21
- Maven 3.9+
- Docker & Docker Compose (pour le run global)
- PostgreSQL 16 (pour run local des services)

## Run en local (sans Docker)

1. **PostgreSQL** : une instance avec une base par service (ou utiliser les noms du `.env.example` et créer les DB à la main).

2. **Créer les bases et utilisateurs** (exemple pour un seul Postgres) :

```sql
CREATE USER auth WITH PASSWORD 'auth';
CREATE DATABASE auth_db OWNER auth;
-- idem pour catalog_db, rental_db, transaction_db, payment_db, notification_db, analytics_db
```

3. **Copier les variables d’environnement** :

```bash
cp .env.example .env
# Ajuster POSTGRES_*, JWT_*, EUREKA_URI selon ton environnement
```

4. **Démarrer dans l’ordre** :

```bash
# 1. Discovery (Eureka)
cd discovery-service && mvn spring-boot:run

# 2. Config (optionnel)
cd config-service && mvn spring-boot:run

# 3. API Gateway
cd api-gateway && mvn spring-boot:run

# 4. Services métier (auth, catalog, rental, transaction, payment, notification, analytics, prediction)
cd auth-service && mvn spring-boot:run
# ... idem pour les autres dans d’autres terminaux
```

5. **Build global** (depuis la racine) :

```bash
mvn clean install
```

## Run avec Docker (dev)

1. **Build des artefacts Maven** :

```bash
mvn clean install -DskipTests
# ou avec les tests :
mvn clean install
```

2. **Build des images et démarrage** :

```bash
docker-compose -f docker-compose.dev.yml build
docker-compose -f docker-compose.dev.yml up -d
```

3. **URLs utiles** :

- Eureka : http://localhost:8761  
- API Gateway : http://localhost:8080  
- Swagger (via Gateway, ex.) :  
  - Auth : http://localhost:8080/api/auth/swagger-ui.html (si exposé)  
  - En direct par service : http://localhost:8081/api/... (auth), http://localhost:8082/api/... (catalog), etc.

## Tests

```bash
# Tous les modules
mvn test

# Un service (ex. auth-service)
cd auth-service && mvn test
```

Chaque service a au moins un **smoke test** avec **Testcontainers** (PostgreSQL).

## Structure des projets

```
carmarket-pro/
├── pom.xml
├── docker-compose.dev.yml
├── .env.example
├── README.md
├── common-lib/           # ApiResponse, Meta, ErrorDetail, CorrelationId
├── discovery-service/
├── api-gateway/
├── config-service/
├── auth-service/         # JWT, RBAC USER/ADMIN/AGENCY
├── catalog-service/
├── rental-service/
├── transaction-service/
├── payment-service/
├── notification-service/
├── analytics-service/
└── prediction-service/   # Prédiction prix (ML via HTTP ou heuristique)
```

Chaque service métier contient notamment :

- `config/` : configuration (sécurité, client Eureka, etc.)
- `controller/` : REST + Swagger
- `dto/`, `domain/`, `repository/`, `service/`, `mapper/`
- `exception/` : `GlobalExceptionHandler`
- `src/main/resources/db/migration/` : scripts Flyway
- `Dockerfile`

## Sécurité (auth-service)

- **Login** : `POST /api/auth/login` → access + refresh token  
- **Register** : `POST /api/auth/register`  
- **Refresh** : `POST /api/auth/refresh` avec `refreshToken`  
- **Rôles** : USER, ADMIN, AGENCY  
- En-tête : `Authorization: Bearer <accessToken>`  
- **X-Correlation-Id** : propagé par le Gateway et les services (logs + appels sortants)

## Prédiction des prix (prediction-service)

- **POST /api/predictions/price** (body : brand, model, year, mileage)  
- **GET /api/predictions/price?brand=...&model=...&year=...&mileage=...**  
- En production : configurer `ML_SERVICE_URL` pour appeler un service ML externe.  
- Sans ML : une heuristique simple est utilisée (démo).

## Licence

Projet à usage interne / démo.
