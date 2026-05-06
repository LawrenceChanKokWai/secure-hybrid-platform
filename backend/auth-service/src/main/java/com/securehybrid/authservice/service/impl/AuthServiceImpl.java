package com.securehybrid.authservice.service.impl;

import com.securehybrid.authservice.dto.request.LoginRequest;
import com.securehybrid.authservice.dto.request.RegisterRequest;
import com.securehybrid.authservice.dto.response.AuthResponse;
import com.securehybrid.authservice.entity.CredentialEntity;
import com.securehybrid.authservice.entity.RefreshTokenEntity;
import com.securehybrid.authservice.entity.RoleEntity;
import com.securehybrid.authservice.entity.UserEntity;
import com.securehybrid.authservice.exception.AuthenticationFailedException;
import com.securehybrid.authservice.exception.ResourceAlreadyExistsException;
import com.securehybrid.authservice.repository.RefreshTokenRepository;
import com.securehybrid.authservice.repository.RoleRepository;
import com.securehybrid.authservice.repository.UserRepository;
import com.securehybrid.authservice.service.AuthService;
import com.securehybrid.authservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email existed in the system");
        }

        RoleEntity userRole = roleRepository.findByNameAndDeletedFalse("ROLE_USER")
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                                .name("ROLE_USER")
                                .description("Default user role")
                                .build()));

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .enabled(true)
                .locked(false)
                .roles(Set.of(userRole))
                .build();

        CredentialEntity credential = CredentialEntity.builder()
                .user(user)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .passwordAlgorithm("bcrypt")
                .passwordChangedAt(Instant.now())
                .failedLoginAttempts(0)
                .build();

        user.setCredential(credential);
        userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        saveRefreshToken(user, refreshToken);

        return buildAuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getCredential().getPasswordHash())) {
            throw new AuthenticationFailedException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        saveRefreshToken(user, refreshToken);

        return buildAuthResponse(accessToken, refreshToken);
    }


    private void saveRefreshToken(UserEntity user, String token) {
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .token(token)
                .expiresAt(
                        Instant.now()
                                .plusSeconds(jwtUtil.getRefreshTokenExpirationDays() * 24 * 60 * 60))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    private AuthResponse buildAuthResponse(String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getAccessTokenExpirationMinutes() * 60)
                .build();
    }
}
