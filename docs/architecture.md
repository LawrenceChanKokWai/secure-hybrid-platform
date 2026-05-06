
# Architecture

## Design Philosophy

Use the right tool for the right job:

* Spring Boot → business logic
* Rust → security-critical processing
* Kafka → event-driven architecture

---

## High-Level Architecture

```text
Client
  ↓
Gateway Service (Spring Boot)
  ↓
Security Service (Rust)
  ↓
Auth Service / User Service
  ↓
Kafka
  ↓
Threat Detection Service (Rust)
  ↓
PostgreSQL / Redis
```

---

## Service Responsibilities

### Gateway Service

* Entry point
* Routes requests
* Calls Rust security-service

### Auth Service

* Registration
* Login
* JWT generation
* Event publishing

### User Service

* Profile management
* RBAC enforcement

### Security Service (Rust)

* JWT validation
* Role checking
* Rate limiting
* IP blocking

### Threat Detection Service (Rust)

* Kafka consumer
* Threat detection
* Alert creation

---

## Database Layer

```text
Primary DB → Writes
Replica DB → Reads
```

All services write to the primary database.

Read-heavy services (such as reporting and audit queries) use the replica database.

Authentication-critical flows always use the primary database.

---

## Request Flow

```text
Client → Gateway → Security Service → Target Service
```

---

## DevOps Architecture

```text
Developer → GitHub → Jenkins → Build/Test → Docker → Deploy
```

---

## Observability

Spring Boot:

* /actuator/health
* /actuator/metrics

Rust:

* metrics endpoint (future)

---

## Versioning

* VERSION file
* Actuator exposure
* Header-based versioning for Rust
