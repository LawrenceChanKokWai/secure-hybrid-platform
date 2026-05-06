# Database Architecture

## Purpose

This document describes the database design for the Secure Hybrid Microservices Platform.

The system uses a **PostgreSQL Primary–Replica architecture** to simulate a production-ready environment.

---

## High-Level Design

```text
Application (Services)
        |
        v
PostgreSQL Primary (Write DB)
        |
        v
PostgreSQL Replica (Read DB)
```

---

## Primary Database

The primary database handles all write operations.

### Responsibilities

* INSERT (new users, tokens, logs)
* UPDATE (profile updates, token rotation)
* DELETE
* Transaction management
* Authentication-critical reads

---

## Replica Database

The replica database handles read-heavy operations.

### Responsibilities

* Read-only queries
* Reporting
* Audit log retrieval
* Analytics queries

---

## Why Use Primary–Replica?

This design improves:

* Read scalability
* System performance under load
* Separation of concerns
* Production readiness
* Fault tolerance awareness

---

## Service Usage Strategy

| Service                  | Write DB | Read DB                      |
| ------------------------ | -------- | ---------------------------- |
| auth-service             | Primary  | Primary (critical reads)     |
| user-service             | Primary  | Replica (non-critical reads) |
| threat-detection-service | Primary  | Replica                      |
| admin/reporting          | Primary  | Replica                      |

---

## Important Rule (Very Important)

Do NOT use replica for critical authentication flows.

### Always use Primary for:

* login validation
* password updates
* refresh token validation
* role changes

Reason:

```text
Replica lag may cause stale data
```

---

## Example Scenario

User updates password:

```text
Write → Primary
Immediate login → Must read from Primary
```

If read from Replica:

```text
Old password may still work ❌
```

---

## Future Enhancements

* Read/write routing layer
* Load balancing between replicas
* Failover handling
* Connection routing via proxy (PgBouncer / HAProxy)

---

## Portfolio Value

This demonstrates:

* understanding of real-world database scaling
* awareness of consistency vs performance trade-offs
* production-ready system design thinking
