# Système de Gestion des Dossiers Médicaux

Application web full-stack : Angular 21 + Spring Boot 3.2 + MySQL

## Prérequis

- Java 17+
- Maven 3.8+
- Node.js 24 + npm
- MySQL 8+

## Démarrage rapide

### 1. Base de données MySQL

```sql
CREATE DATABASE IF NOT EXISTS pi_s3 CHARACTER SET utf8mb4;
```

Le schéma est créé automatiquement par Hibernate (`ddl-auto=update`).
Les données de test sont injectées depuis `data.sql`.

### 2. Backend Spring Boot

```bash
cd backend
mvn spring-boot:run
```
→ API disponible sur http://localhost:8080

### 3. Frontend Angular

```bash
cd frontend
npm install
ng serve
```
→ Application sur http://localhost:4200

## Comptes de démonstration

| Utilisateur            | Mot de passe | Rôle        |
|------------------------|------------- |-------------|
| admin@medical.com      | password1    | ADMIN       |
-------------------------|--------------|-------------|


## Structure du projet

```
PI_S3/
├── backend/                     ← Spring Boot 3.2
│   └── src/main/java/com/medical/dossier/
│       ├── config/              ← JWT, Security, CORS
│       ├── controller/          ← REST API endpoints
│       ├── dto/                 ← Data Transfer Objects
│       ├── entity/              ← JPA entities
│       ├── exception/           ← Error handling
│       ├── repository/          ← Spring Data JPA
│       └── service/             ← Business logic
└── frontend/                    ← Angular 21
    └── src/app/
        ├── core/                ← Services, Guards, Models, Interceptors
        ├── features/            ← Pages (auth, dashboard, patients, ...)
        └── shared/              ← Layout, Sidebar

## API Endpoints

| Méthode | Endpoint                    | Accès          |
|---------|-----------------------------|----------------|
| POST    | /api/auth/login             | Public         |
| POST    | /api/auth/register          | Public         |
| GET     | /api/patients               | Tous           |
| POST    | /api/patients               | ASSISTANT/ADMIN|
| GET     | /api/consultations          | Tous           |
| POST    | /api/consultations          | MEDECIN/ADMIN  |
| GET     | /api/rendez-vous            | Tous           |
| POST    | /api/rendez-vous            | Tous           |
| GET     | /api/services               | Tous           |
| GET     | /api/dashboard/stats        | Tous           |
| GET     | /api/utilisateurs           | ADMIN          |
```
