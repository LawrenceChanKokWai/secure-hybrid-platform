# Java-Rust Mapping

## Purpose

Show equivalence between Rust and Java concepts.

---

## Mapping

| Rust        | Java                   |
| ----------- | ---------------------- |
| Axum        | Spring Boot Controller |
| Tokio       | WebFlux                |
| SQLx        | JPA                    |
| rdkafka     | Spring Kafka           |
| Serde       | Jackson                |
| Redis crate | Spring Redis           |

---

## Example

Rust:

```rust
async fn handler() {}
```

Java:

```java
@GetMapping
public ResponseEntity<?> handler() {}
```

---

## Explanation

Spring Boot:

* business logic

Rust:

* performance & security

---

## Positioning

Hybrid backend engineer with security focus
