
erDiagram
USERS ||--|| CREDENTIALS : has
USERS ||--o{ REFRESH_TOKENS : owns
USERS ||--o{ USER_ROLES : assigned
ROLES ||--o{ USER_ROLES : contains

    USERS {
        bigint id PK
        varchar email UK
        boolean enabled
        boolean locked
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    CREDENTIALS {
        bigint id PK
        bigint user_id FK
        varchar password_hash
        varchar password_algorithm
        timestamp password_changed_at
        int failed_login_attempts
        timestamp last_failed_login_at
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    ROLES {
        bigint id PK
        varchar name UK
        varchar description
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    USER_ROLES {
        bigint user_id FK
        bigint role_id FK
    }

    REFRESH_TOKENS {
        bigint id PK
        bigint user_id FK
        varchar token UK
        timestamp expires_at
        boolean revoked
        timestamp revoked_at
        varchar replaced_by_token
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    USER_PROFILES ||--o{ ADDRESSES : has

    USER_PROFILES {
        bigint id PK
        bigint auth_user_id UK
        varchar email UK
        varchar full_name
        varchar display_name
        varchar phone_number
        varchar profile_image_url
        varchar status
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    ADDRESSES {
        bigint id PK
        bigint user_profile_id FK
        varchar address_line_1
        varchar address_line_2
        varchar city
        varchar state
        varchar postal_code
        varchar country
        varchar address_type
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    SECURITY_EVENTS ||--o{ SECURITY_ALERTS : may_trigger
    BLOCKED_IPS ||--o{ SECURITY_ALERTS : related_to

    SECURITY_EVENTS {
        bigint id PK
        varchar event_id UK
        varchar event_type
        bigint user_id
        varchar email
        varchar ip_address
        varchar user_agent
        varchar source_service
        jsonb metadata
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    SECURITY_ALERTS {
        bigint id PK
        varchar alert_type
        varchar severity
        bigint user_id
        varchar email
        varchar ip_address
        varchar message
        varchar status
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        timestamp resolved_at
        varchar resolved_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    BLOCKED_IPS {
        bigint id PK
        varchar ip_address UK
        varchar reason
        timestamp blocked_until
        boolean active
        timestamp created_at
        timestamp updated_at
        varchar created_by
        varchar updated_by
        boolean deleted
        timestamp deleted_at
        varchar deleted_by
        boolean archived
        timestamp archived_at
    }

    LOG_ARCHIVE_JOBS {
        bigint id PK
        varchar service_name
        varchar log_date
        varchar source_path
        varchar destination_path
        varchar file_name
        varchar status
        timestamp started_at
        timestamp completed_at
        varchar error_message
    }

    DATA_ARCHIVE_JOBS {
        bigint id PK
        varchar source_database
        varchar source_table
        varchar archive_database
        varchar archive_table
        int records_found
        int records_archived
        varchar status
        timestamp started_at
        timestamp completed_at
        varchar error_message
    }

    ARCHIVED_USERS {
        bigint archive_id PK
        bigint original_id
        varchar email
        boolean enabled
        boolean locked
        timestamp created_at
        timestamp updated_at
        timestamp deleted_at
        timestamp archived_at
    }

    ARCHIVED_USER_PROFILES {
        bigint archive_id PK
        bigint original_id
        bigint auth_user_id
        varchar email
        varchar full_name
        varchar display_name
        varchar phone_number
        varchar status
        timestamp created_at
        timestamp updated_at
        timestamp deleted_at
        timestamp archived_at
    }

    ARCHIVED_SECURITY_EVENTS {
        bigint archive_id PK
        bigint original_id
        varchar event_id
        varchar event_type
        bigint user_id
        varchar email
        varchar ip_address
        varchar source_service
        jsonb metadata
        timestamp created_at
        timestamp deleted_at
        timestamp archived_at
    }

    DATA_ARCHIVE_JOBS ||--o{ ARCHIVED_USERS : archives
    DATA_ARCHIVE_JOBS ||--o{ ARCHIVED_USER_PROFILES : archives
    DATA_ARCHIVE_JOBS ||--o{ ARCHIVED_SECURITY_EVENTS : archives