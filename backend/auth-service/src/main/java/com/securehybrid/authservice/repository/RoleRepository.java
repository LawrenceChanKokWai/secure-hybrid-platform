package com.securehybrid.authservice.repository;

import com.securehybrid.authservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByNameAndDeletedFalse(String name);
}
