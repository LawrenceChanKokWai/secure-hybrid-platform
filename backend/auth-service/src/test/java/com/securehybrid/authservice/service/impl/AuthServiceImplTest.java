package com.securehybrid.authservice.service.impl;

import com.securehybrid.authservice.TestUtil;
import com.securehybrid.authservice.dto.request.LoginRequest;
import com.securehybrid.authservice.dto.request.RegisterRequest;
import com.securehybrid.authservice.dto.response.AuthResponse;
import com.securehybrid.authservice.entity.RoleEntity;
import com.securehybrid.authservice.entity.UserEntity;
import com.securehybrid.authservice.exception.AuthenticationFailedException;
import com.securehybrid.authservice.exception.ResourceAlreadyExistsException;
import com.securehybrid.authservice.repository.RefreshTokenRepository;
import com.securehybrid.authservice.repository.RoleRepository;
import com.securehybrid.authservice.repository.UserRepository;
import com.securehybrid.authservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RefreshTokenRepository refreshTokenRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthServiceImpl authService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        refreshTokenRepository = Mockito.mock(RefreshTokenRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        jwtUtil = Mockito.mock(JwtUtil.class);

        authService = new AuthServiceImpl(
                userRepository,roleRepository,refreshTokenRepository,passwordEncoder,jwtUtil);
    }

    @Test
    void register_shouldReturnTokens_whenEmailIsNew() {
        RegisterRequest request = TestUtil.createRegisterRequest();
        RoleEntity role = TestUtil.createUserRole();

        when(userRepository.existsByEmailAndDeletedFalse(
                request.getEmail())).thenReturn(false);
        when(roleRepository.findByNameAndDeletedFalse("ROLE_USER"))
                .thenReturn(Optional.of(role));
        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("hashed-password");
        when(jwtUtil.generateAccessToken(request.getEmail()))
                .thenReturn("access-token");
        when(jwtUtil.generateRefreshToken(request.getEmail()))
                .thenReturn("refresh-token");
        when(jwtUtil.getRefreshTokenExpirationDays()).thenReturn(7L);
        when(jwtUtil.getAccessTokenExpirationMinutes()).thenReturn(15L);

        AuthResponse response = authService.register(request);
        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(900, response.getExpiresIn());

        verify(userRepository).save(any(UserEntity.class));
        verify(refreshTokenRepository).save(any());
    }

    @Test
    void register_shouldThrowException_whenEmailExist() {
        RegisterRequest request = TestUtil.createRegisterRequest();

        when(userRepository.existsByEmailAndDeletedFalse(request.getEmail()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class,
                () -> authService.register(request));
    }

    @Test
    void login_shouldReturnTokens_whenCredentialsAreValid() {
        LoginRequest request = TestUtil.createLoginRequest();
        UserEntity user = TestUtil.createUserEntity();

        when(userRepository.findByEmailAndDeletedFalse(
                request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getCredential().getPasswordHash()))
                .thenReturn(true);
        when(jwtUtil.generateAccessToken(request.getEmail()))
                .thenReturn("access-token");
        when(jwtUtil.generateRefreshToken(request.getEmail()))
                .thenReturn("refresh-token");
        when(jwtUtil.getRefreshTokenExpirationDays())
                .thenReturn(7L);
        when(jwtUtil.getAccessTokenExpirationMinutes())
                .thenReturn(15L);

        AuthResponse response = authService.login(request);
        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());

        verify(refreshTokenRepository).save(any());
    }

    @Test
    void login_shouldThrowException_whenPasswordIsWrong() {
        LoginRequest request = TestUtil.createInvalidLoginRequest();
        UserEntity user = TestUtil.createUserEntity();

        when(userRepository.findByEmailAndDeletedFalse(request.getEmail()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getCredential().getPasswordHash()))
                .thenReturn(false);

        assertThrows(
                AuthenticationFailedException.class,
                () -> authService.login(request)
        );
        verify(refreshTokenRepository, never()).save(any());
    }
}