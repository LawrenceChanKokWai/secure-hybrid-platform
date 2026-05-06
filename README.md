
# Secure Hybrid Microservices Platform

## Overview

This project is a **security-focused hybrid microservices platform** designed to demonstrate:

* Backend engineering using Spring Boot
* High-performance security services using Rust
* Event-driven architecture using Kafka
* Concurrency-safe system design
* Real-world attack simulation and defence
* DevOps practices including CI/CD and monitoring

This is a **production-style portfolio project**, not a tutorial.

---

## Architecture Summary

```text
Client → Gateway (Spring Boot) → Security Service (Rust)
        → Auth Service (Spring Boot)
        → User Service (Spring Boot)
        → Kafka → Threat Detection (Rust)
```

---

## Technology Stack

### Backend (Java)

* Spring Boot
* Spring Security
* Spring Cloud Gateway
* Spring Data JPA

### Security & Performance (Rust)

* Axum
* Tokio
* SQLx
* Redis
* rdkafka

### Infrastructure

* PostgreSQL
* Kafka (KRaft mode)
* Redis
* Docker

---

## Core Services

| Service                  | Tech        | Purpose                         |
| ------------------------ | ----------- | ------------------------------- |
| gateway-service          | Spring Boot | Entry point and routing         |
| auth-service             | Spring Boot | Authentication and JWT          |
| user-service             | Spring Boot | User profile                    |
| security-service         | Rust        | Token validation, rate limiting |
| threat-detection-service | Rust        | Event-driven threat detection   |

---

## Key Security Features

* JWT authentication
* Refresh token rotation
* Role-Based Access Control (RBAC)
* Rust-based security enforcement
* Rate limiting (Redis)
* IP blocking
* Kafka audit logging
* Brute-force detection
* Security alerts

---

## Concurrency & Performance

* Stateless Spring Boot services
* Tokio async Rust services
* Redis for distributed state
* Kafka partitions for parallel processing
* Database connection pooling

---

## Database Architecture

The system uses PostgreSQL with a Primary–Replica design:

* Primary database handles all writes
* Replica database handles read-heavy operations

Critical authentication flows always use the primary database to avoid stale data caused by replication lag.

---

## DevOps & Quality Engineering

### Testing

* Unit tests (JUnit + cargo test)
* Integration tests (Testcontainers)
* Security scenario testing

### CI/CD

* Jenkins pipeline
* GitHub version control
* Optional GitHub Actions

### Observability

* Spring Boot Actuator endpoints
* Health and metrics monitoring
* Logging and tracing ready

### Versioning

* Semantic versioning (MAJOR.MINOR.PATCH)
* VERSION file
* `/actuator/info` exposure

---

## CI/CD Flow

```text
GitHub → Jenkins → Build → Test → Docker → Deploy
```

---

## Portfolio Value

This project demonstrates:

* Secure backend design
* Hybrid Java + Rust architecture
* Event-driven systems
* Concurrency handling
* Real-world attack defence
* CI/CD and DevOps practices
* Observability and monitoring

---

## Author Positioning

Backend Engineer specialising in:

* Microservices architecture
* Security engineering
* High-performance systems (Rust)
