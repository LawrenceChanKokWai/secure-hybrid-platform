# Concurrency Design

## Goals

* handle many requests
* ensure consistency
* avoid race conditions

---

## Spring Boot

* stateless
* HikariCP
* transactions

---

## Rust

* Tokio async
* non-blocking
* Arc shared state

---

## Database Concurrency

Primary–Replica design helps handle concurrency:

* Primary handles write contention
* Replica handles read scaling

This prevents read-heavy workloads from affecting write performance.

Replica lag is considered when designing authentication flows.

---

## Redis

* distributed counters
* rate limiting

---

## Kafka

* partitioned topics
* parallel consumers

---

## Avoid

* shared mutable state
* blocking calls
* local-only counters

---

## Testing

* concurrent logins
* rate limit tests
* Kafka load

---

## Expected Outcome

* scalable system
* consistent behaviour
