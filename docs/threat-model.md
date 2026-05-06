# Threat Model

## Assets

* user accounts
* tokens
* APIs
* audit logs

---

## Threats & Defences

### Brute Force

Defence:

* Kafka detection
* IP block

### Token Tampering

Defence:

* signature validation

### Access Control

Defence:

* RBAC enforcement

### Token Replay

Defence:
* expiry validation

### API Abuse

Defence:
* rate limiting

### Credential Stuffing

Defence:

* behaviour detection

### Refresh Token Abuse

Defence:

* rotation

### Race Conditions

Defence:

* Redis atomic ops

---

## Threat Detection Flow

```text
Event → Kafka → Rust Detection → Alert → Action
```

---

## Threat: Replica Lag Exploitation

### Scenario

An attacker attempts to exploit replication delay to bypass authentication checks.

### Risk

* outdated password validation
* stale role data

### Defence

* critical authentication reads always use primary database
* replica used only for non-critical queries

---

## Automated Response

* block IP
* create alert
* log event
