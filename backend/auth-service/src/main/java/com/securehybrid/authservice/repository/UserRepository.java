package com.securehybrid.authservice.repository;

import com.securehybrid.authservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndDeletedFalse(String email);
    boolean existsByEmailAndDeletedFalse(String email);
}
