
# Fitness Monolith Backend

A cloud-native, RESTful backend API for fitness tracking, built using **Spring Boot 4.0.1** and **Java 21**.  
This project demonstrates a **production-grade monolithic architecture** with secure authentication, cloud-hosted PostgreSQL integration (NeonDB), and containerized deployment using Docker and Render.

---

## Project Links

- **Live Base URL:**  
  https://fitness-mono-9yj0.onrender.com

- **Swagger API Documentation:**  
  https://fitness-mono-9yj0.onrender.com/swagger-ui/index.html

- **Docker Hub Image:**  
  https://hub.docker.com/r/sidbhadu5712/fitness-mono

---

## Table of Contents

- [Overview](#overview)
- [Architecture and Code Structure](#architecture-and-code-structure)
- [Technical Implementation Details](#technical-implementation-details)
- [Database Schema and Relations](#database-schema-and-relations)
- [Local Development Setup](#local-development-setup)
- [Docker Containerization](#docker-containerization)
- [Cloud Deployment (NeonDB & Render)](#cloud-deployment-neondb--render)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)
- [License](#license)
- [Author](#author)

---

## Overview

The Fitness Monolith Backend serves as the core backend system for a comprehensive fitness tracking platform.  
It enables users to:

- Register and authenticate securely
- Log fitness activities (running, cycling, swimming, etc.)
- Receive performance-based workout recommendations

The application follows a **stateless architecture**, using **JWT (JSON Web Tokens)** for authentication and persisting data in a cloud-hosted PostgreSQL database.

---

## Architecture and Code Structure

The project follows a **Layered Monolithic Architecture**, ensuring clear separation of concerns and long-term maintainability.

### 1. Controller Layer  
`src/main/java/com/example/fitness/controller`

Handles incoming HTTP requests, validates input using DTOs, and delegates processing to the Service layer.

- **AuthController** – User registration and authentication
- **ActivityController** – Workout logging and retrieval
- **RecommendationController** – Workout feedback and recommendations

---

### 2. Service Layer  
`src/main/java/com/example/fitness/service`

Contains core business logic and orchestrates operations across repositories.

- **UserService** – User creation, password encoding, DTO mapping
- **ActivityService** – Activity processing and user association
- **RecommendationService** – Performance-based feedback generation

---

### 3. Repository Layer  
`src/main/java/com/example/fitness/repository`

Uses **Spring Data JPA** for database access.  
Repositories extend `JpaRepository`, providing built-in CRUD functionality without manual SQL.

---

### 4. Model / Entity Layer  
`src/main/java/com/example/fitness/model`

Defines database entities using JPA annotations such as `@Entity`, `@Table`, and `@Id`, mapping Java objects directly to database tables.

---

### 5. DTO Layer  
`src/main/java/com/example/fitness/dto`

Defines the API contract for request and response payloads.  
This ensures decoupling between persistence models and external API consumers.

---

### 6. Configuration Layer  
`src/main/java/com/example/fitness/config`

Holds application-wide configurations such as:

- OpenAPI (Swagger) configuration
- Security and filter chain configuration
- Bean definitions

---

## Technical Implementation Details

### Spring Security and JWT Authentication

The application implements **stateless security** using JWT.

- **SecurityConfig**
  - Defines `SecurityFilterChain`
  - Disables CSRF
  - Configures public and protected endpoints
  - Registers JWT authentication filter

- **JwtAuthenticationFilter**
  - Extends `OncePerRequestFilter`
  - Intercepts every request
  - Extracts and validates `Authorization: Bearer <token>`
  - Sets authentication context

- **JwtUtils**
  - Generates signed JWT tokens using HMAC-SHA256
  - Extracts claims (user ID, roles)
  - Validates token expiration

- **Password Encoding**
  - Uses `BCryptPasswordEncoder`
  - Ensures secure password hashing before persistence

---

### Lombok Usage

To minimize boilerplate code, **Project Lombok** is extensively used:

- `@Data` – Generates getters, setters, equals, hashCode, and toString
- `@Builder` – Enables builder pattern
- `@RequiredArgsConstructor` – Supports constructor-based dependency injection

---

### Dependency Injection and Bean Management

Spring’s IoC container manages dependencies via:

- `@RestController`
- `@Service`
- `@Repository`
- `@Component`
- `@Configuration` and `@Bean` for third-party objects

---

## Database Schema and Relations

Hibernate automatically generates tables using:

```properties
spring.jpa.hibernate.ddl-auto=update
````

### Entities

1. **User (`fitness_user`)**
   Stores authentication credentials and profile data
   (custom table name avoids conflict with SQL keyword `user`)

2. **Activity**
   Stores workout session data

3. **Recommendation**
   Stores feedback and recommendation logic

---

### Relationships

* **User → Activity**
  One-to-Many

* **User → Recommendation**
  One-to-Many

* **Activity → Recommendation**
  One-to-Many

---

### Foreign Keys

Automatically generated by Hibernate:

* `user_id` in `activity`
* `user_id` and `activity_id` in `recommendation`

---

## Local Development Setup

### Prerequisites

* Java 21 SDK
* Maven
* PostgreSQL or MySQL
* Git

---

### Step 1: Clone the Repository

```bash
git clone https://github.com/sidhu1512/SpringBoot-production-App.git
cd SpringBoot-production-App
```

---

### Step 2: Configure the Database

#### Option A: PostgreSQL (Recommended)

1. Create database `fitness_tracker`
2. Set environment variables:

```text
DB_URL=jdbc:postgresql://localhost:5432/fitness_tracker
DB_USER=postgres
DB_PSWD=your_password
```

3. Driver: `org.postgresql.Driver`

---

#### Option B: MySQL

1. Create schema `fitness_tracker`
2. Update environment variables accordingly
3. Enable MySQL driver and disable PostgreSQL dependency in `pom.xml`
4. Driver: `com.mysql.cj.jdbc.Driver`

---

### Step 3: Run the Application

#### Windows

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/fitness_tracker"
$env:DB_USER="postgres"
$env:DB_PSWD="password"
./mvnw spring-boot:run
```

#### Linux / macOS

```bash
export DB_URL="jdbc:postgresql://localhost:5432/fitness_tracker"
export DB_USER="postgres"
export DB_PSWD="password"
./mvnw spring-boot:run
```

---

## Docker Containerization

### Build the Image

```bash
./mvnw clean package -DskipTests
docker build -t fitness-monolith .
```

---

### Run the Container

```bash
docker run -p 8080:8080 \
  -e DB_URL="jdbc:postgresql://host.docker.internal:5432/fitness_tracker" \
  -e DB_USER="postgres" \
  -e DB_PSWD="your_password" \
  fitness-monolith
```

---

## Cloud Deployment (NeonDB & Render)

### NeonDB Setup

1. Create account on Neon.tech
2. Create a PostgreSQL project
3. Copy the JDBC connection string
4. Tables are auto-created via Hibernate

---

### Render Deployment

1. Push Docker image:

```bash
docker tag fitness-monolith sidbhadu5712/fitness-mono
docker push sidbhadu5712/fitness-mono
```

2. Create a new **Web Service** on Render
3. Deploy from existing Docker image
4. Configure environment variables:

```text
DB_URL=jdbc:postgresql://host/db?sslmode=require
DB_USER=neon_user
DB_PSWD=neon_password
```

---

## Testing

### Swagger UI

* **Local:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **Production:** [https://fitness-mono-9yj0.onrender.com/swagger-ui/index.html](https://fitness-mono-9yj0.onrender.com/swagger-ui/index.html)

---

### Postman Workflow

1. **Register User**
   `POST /api/auth/register`

2. **Login**
   `POST /api/auth/login`
   Copy JWT token from response

3. **Access Protected APIs**
   Use `Authorization: Bearer <token>`

---

## Future Enhancements

* CI/CD pipeline using GitHub Actions
* Frontend dashboard using React

---

## License

Licensed under the **Apache 2.0 License**.
See the `LICENSE` file for details.

---

## Author

**Siddharth Bhadu**
Software Engineer | Java & Spring Boot Specialist

* GitHub: [https://github.com/sidhu1512](https://github.com/sidhu1512)
* Email: [SiddharthBhadu.work@gmail.com](mailto:SiddharthBhadu.work@gmail.com)


