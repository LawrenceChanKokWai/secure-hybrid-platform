package com.securehybrid.authservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false, length = 100)
    private String updatedBy;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(length = 100)
    private String deletedBy;

    private Instant deletedAt;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private boolean archived = false;

    private Instant archiveAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;

        if(this.createdBy == null) {
            this.createdBy = "SYSTEM";
        }
        if(this.updatedBy == null) {
            this.updatedBy = "SYSTEM";
        }

        this.deleted = false;
        this.archived = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();

        if(this.updatedBy == null) {
            this.updatedBy = "SYSTEM";
        }
    }

    public void softDelete(String deletedBy) {
        this.deleted = true;
        this.deletedBy = deletedBy;
        this.deletedAt = Instant.now();
    }

    public void markArchived() {
        this.archived = true;
        this.archiveAt = Instant.now();
    }
}
