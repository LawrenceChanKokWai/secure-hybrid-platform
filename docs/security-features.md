# Security Features

## Authentication

* JWT authentication
* Access token expiry
* Refresh token rotation

---

## Authorization

* Role-Based Access Control
* Endpoint protection

---

## Rust Security Enforcement

Centralised decision:

```text
ALLOW / DENY
```

Checks:

* token validity
* expiry
* roles
* IP
* rate limit

---

## Rate Limiting

Example:

```text
Login → 5 requests/min/IP
```

---

## IP Blocking

Triggered by:

* repeated failed logins

---

## Kafka Audit Events

* login success
* login failure
* user updates
* alerts

---

## Threat Detection

Detects:

* brute-force attacks
* suspicious IPs
* abnormal access

---

## Security Alerts

```json
{
  "type": "BRUTE_FORCE",
  "severity": "HIGH"
}
```

---

## Defence-in-Depth

1. Gateway
2. Rust security layer
3. Spring Boot RBAC
4. Kafka monitoring

---

## Database Security Considerations

* Critical reads use primary database
* Replica used only for non-sensitive operations
* Prevents stale data exploitation

---

## Security Testing

* brute-force simulation
* invalid token tests
* rate limit stress tests
