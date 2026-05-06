package com.securehybrid.authservice;

import com.securehybrid.authservice.dto.request.LoginRequest;
import com.securehybrid.authservice.dto.request.RegisterRequest;
import com.securehybrid.authservice.entity.CredentialEntity;
import com.securehybrid.authservice.entity.RoleEntity;
import com.securehybrid.authservice.entity.UserEntity;

import java.time.Instant;
import java.util.Set;

public class TestUtil {
    private TestUtil() {}

    public static String testEmail = "test@test.com";
    public static String testNewEmail = "newtest@test.com";
    public static String testPassword = "password123";
    public static String wrongPassword = "wrong_password";
    public static String testRole = "ROLE_USER";
    public static String testDescription = "Default user role";
    public static String hashedPassword = "hashed-password";
    public static String passwordStrategy = "bcrypt";

    public static RegisterRequest createRegisterRequest() {
        return RegisterRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
    }

    public static RegisterRequest createNewRegisterRequest() {
        return RegisterRequest.builder()
                .email(testNewEmail)
                .password(testPassword)
                .build();
    }

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
    }

    public static LoginRequest createNewLoginRequest() {
        return LoginRequest.builder()
                .email(testNewEmail)
                .password(testPassword)
                .build();
    }

    public static LoginRequest createInvalidLoginRequest() {
        return LoginRequest.builder()
                .email(testEmail)
                .password(wrongPassword)
                .build();
    }

    public static RoleEntity createUserRole() {
        return RoleEntity.builder()
                .name(testRole)
                .description(testDescription)
                .build();
    }

    public static CredentialEntity createCredentialEntity(UserEntity user) {
        return CredentialEntity.builder()
                .user(user)
                .passwordHash(hashedPassword)
                .passwordAlgorithm(passwordStrategy)
                .passwordChangedAt(Instant.now())
                .failedLoginAttempts(0)
                .build();
    }

    public static UserEntity createUserEntity() {
        UserEntity user = UserEntity.builder()
                .email(testEmail)
                .enabled(true)
                .locked(false)
                .roles(Set.of(createUserRole()))
                .build();

        CredentialEntity credential = createCredentialEntity(user);
        user.setCredential(credential);

        return user;
    }

    public static String uniqueEmail() {
        return "test_" + System.currentTimeMillis() + "@test.com";
    }

    public static RegisterRequest createRegisterRequest(String email) {
        return RegisterRequest.builder()
                .email(email)
                .password(testPassword)
                .build();
    }

    public static LoginRequest createLoginRequest(String email) {
        return LoginRequest.builder()
                .email(email)
                .password(testPassword)
                .build();
    }

    public static LoginRequest createInvalidLoginRequest(String email) {
        return LoginRequest.builder()
                .email(email)
                .password(wrongPassword)
                .build();
    }
}
