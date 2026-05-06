package com.securehybrid.authservice.repository;

import com.securehybrid.authservice.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {}
