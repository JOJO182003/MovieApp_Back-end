## ✅ 1. Arborescence générale du projet

```
com.movieapp/
├── application/
│   └── service/                   # Services métiers (UserService, MovieService, etc.)
├── controller/
│   └── rest/                      # Contrôleurs REST exposant les endpoints
├── domain/
│   ├── model/                     # Modèles métiers purs (DDD, immuables)
│   └── repository/                # Interfaces abstraites pour les repos
├── dto/
│   ├── request/                   # Objets reçus via l'API
│   └── response/                  # Objets renvoyés par l'API
├── exception/                     # Gestion centralisée des exceptions
├── infrastructure/
│   └── persistence/
│       ├── entity/               # Entités JPA mappées à la base de données
│       ├── mapper/               # Mapping Entity ↔ Domain
│       └── adapter/              # Implémentations concrètes des repos
├── mapper/
│   └── rest/                      # Mapping Domain ↔ DTO REST
├── security/                      # Authentification, JWT, filtre, etc.
├── util/                          # `TestDataFactory`, helpers de test
└── MovieAppApplication.java       # Point d'entrée de l'application
```

---

## ✅ 2. Plan conseillé pour le rapport

### 1. **Introduction**

* Contexte du projet
* Objectif de l'API
* Stack utilisée (Java, Spring Boot, JPA, JWT, etc.)

### 2. **Architecture technique**

* Description de la structure en couches
* Explication du découplage :

  * `domain.model` : logique métier
  * `infrastructure.persistence` : persistance JPA
  * `controller.rest` : API REST
  * `dto` : encapsulation des échanges

### 3. **Authentification / Sécurité**

* JWT mis en place
* Filtres (`JwtAuthenticationFilter`)
* Protection des endpoints (`SecurityConfig`)
* Rôles et contrôle d’accès

### 4. **Gestion des erreurs**

* `GlobalExceptionHandler`
* Exceptions métiers (`NotFoundException`, `UnauthorizedException`, etc.)

### 5. **Base de données**

* Entités JPA (`UserEntity`, `MovieEntity`, etc.)
* Tables associées (clé composite, relations `@ManyToMany`)
* Audit (`createdAt`, `updatedAt` via `@PrePersist`)

### 6. **Tests**

* Couverture partielle (\~75% complétée)
* Tests unitaires par couche :

  * `domain.model` (validation métier)
  * `application.service` (avec mocks)
  * `controller.rest` (avec `MockMvc`)
* Utilisation de `TestDataFactory` pour centraliser les données

### 7. **Bonnes pratiques appliquées**

* Clean Architecture / DDD
* Séparation des responsabilités
* Validation via annotations `jakarta.validation`
* Aucune logique métier dans les contrôleurs

### 8. **Ce qu’il reste à faire (facultatif)**

* Finaliser les 25% restants de tests
* Ajouter un README technique ?
* Déploiement / CI/CD (si prévu)

### 9. **Conclusion**

* Retour sur les difficultés rencontrées
* Ce que vous avez appris
* Possibilités d’évolution (pagination, logging avancé, etc.)

---

## ✅ Où regarder quoi

| Sujet à documenter  | Où regarder dans le code                           |
| ------------------- | -------------------------------------------------- |
| Architecture métier | `domain.model`, `application.service`              |
| API REST            | `controller.rest.*` + `dto.request/response`       |
| JWT + Sécurité      | `security/`, `SecurityConfig`, `JwtProvider`       |
| Base de données     | `infrastructure.persistence.entity`                |
| Mapping             | `infrastructure.persistence.mapper`, `mapper.rest` |
| Erreurs             | `exception/`                                       |
| Tests               | `src/test/java/` (utilise `TestDataFactory`)       |

---

Souhaites-tu que je fournisse une trame `.docx` ou `.md` déjà pré-remplie avec ce squelette ?
