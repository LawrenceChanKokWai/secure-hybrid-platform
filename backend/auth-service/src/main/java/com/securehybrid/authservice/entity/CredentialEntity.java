package com.securehybrid.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credentials")
public class CredentialEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private UserEntity user;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 50)
    private String passwordAlgorithm;

    @Column(nullable = false)
    private Instant passwordChangedAt;

    @Column(nullable = false)
    private int failedLoginAttempts;

    private Instant lastFailedLoginAt;

}
