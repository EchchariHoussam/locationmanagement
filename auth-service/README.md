# Auth Service

Microservice **authentification + JWT + rôles** pour CarMarket Pro.

## Responsabilités

- **Inscription** : email + password (hash BCrypt)
- **Login** → JWT Access + Refresh
- **Refresh token** : renouveler l’access token
- **Logout** : invalidation du refresh token (champ `revoked` en base)
- **Rôles** : USER, ADMIN, AGENCY (tables `roles` + `user_roles`)
- **OAuth2 (Google)** : prévu en TODO (voir section ci-dessous)

## Base de données (PostgreSQL)

- **users** : id, email, password_hash, enabled, created_at, updated_at
- **roles** : id, name (USER, ADMIN, AGENCY)
- **user_roles** : user_id, role_id
- **refresh_tokens** : id, user_id, token, expires_at, revoked

Migrations : **Flyway** (`src/main/resources/db/migration`).

## Endpoints

| Méthode | Path | Description |
|--------|------|-------------|
| POST | /api/auth/register | Inscription (email + password) |
| POST | /api/auth/login | Login → access + refresh token |
| POST | /api/auth/refresh | Nouvel access token avec refresh token |
| POST | /api/auth/logout | Invalider le refresh token (revoked) |
| GET | /api/auth/me | User connecté + rôles (Bearer requis) |

Réponses au **format API standard** :

```json
{
  "success": true,
  "message": "string",
  "data": { ... },
  "errors": [],
  "meta": null
}
```

## Tech

- **Spring Boot 3.3+**, **Spring Security 6**, **JWT** (jjwt)
- Filtre JWT + SecurityConfig (stateless)
- Flyway, Swagger (springdoc-openapi), Actuator (health)

## Lancer en local

### Prérequis

- JDK 21
- Maven 3.9+
- PostgreSQL 16 (ou Docker)

### 1. PostgreSQL

Créer une base et un utilisateur :

```sql
CREATE USER auth WITH PASSWORD 'auth';
CREATE DATABASE auth_db OWNER auth;
```

### 2. Variables d’environnement

```bash
export POSTGRES_HOST=localhost
export POSTGRES_PORT=5432
export POSTGRES_DB=auth_db
export POSTGRES_USER=auth
export POSTGRES_PASSWORD=auth
export JWT_ACCESS_SECRET=your-256-bit-access-secret-key-for-carmarket-pro-auth
export JWT_REFRESH_SECRET=your-256-bit-refresh-secret-key-for-carmarket-pro
```

(Voir `.env.example` à la racine du projet ou copier depuis le parent.)

### 3. Lancer le service

```bash
cd auth-service
mvn spring-boot:run
```

- API : http://localhost:8081  
- Swagger : http://localhost:8081/swagger-ui.html  
- Actuator health : http://localhost:8081/actuator/health  

## Lancer avec Docker (auth + PostgreSQL)

À la racine du repo (ou depuis `auth-service`) :

```bash
# Build du JAR
mvn clean install -pl auth-service -am -DskipTests

# Lancer PostgreSQL + Auth Service
docker-compose -f auth-service/docker-compose.yml up -d

# Logs
docker-compose -f auth-service/docker-compose.yml logs -f auth-service
```

- API : http://localhost:8081  
- Health : http://localhost:8081/actuator/health  

## Tests

```bash
cd auth-service
mvn test
```

Tests inclus :

- **AuthServiceApplicationTests** : contexte + Testcontainers (PostgreSQL)
- **AuthControllerIntegrationTest** : login, refresh, accès à un endpoint protégé (GET /api/auth/me)

## OAuth2 Login (Google) – TODO

L’intégration **OAuth2 / Google** est prévue mais non implémentée. À faire :

1. Dépendance `spring-boot-starter-oauth2-client`.
2. Configuration `spring.security.oauth2.client.registration.google` (client-id, client-secret).
3. Endpoint optionnel (ex. `GET /api/auth/oauth2/google` ou redirection vers le fournisseur).
4. Après callback Google : créer ou lier un utilisateur et émettre JWT Access + Refresh comme pour le login classique.

Documenter les étapes et les variables d’environnement (client-id, client-secret) dans ce README une fois implémenté.
