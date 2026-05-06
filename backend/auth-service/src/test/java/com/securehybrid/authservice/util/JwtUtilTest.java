package com.securehybrid.authservice.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static com.securehybrid.authservice.TestUtil.testEmail;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        ReflectionTestUtils.setField(
                jwtUtil, "jwtSecret", "this-is-the-long-long-jwt-secret-key-just-for-testing"
        );
        ReflectionTestUtils.setField(
                jwtUtil, "accessTokenExpirationMinutes", 15L
        );
        ReflectionTestUtils.setField(
                jwtUtil, "refreshTokenExpirationDays", 7L
        );
        jwtUtil.init();
    }

    @Test
    void shouldGenerateAccessToken() {
        String accessToken = jwtUtil.generateAccessToken(testEmail);

        assertNotNull(accessToken);
        assertFalse(accessToken.isBlank());
    }

    @Test
    void shouldGenerateRefreshToken() {
        String refreshToken = jwtUtil.generateAccessToken(testEmail);

        assertNotNull(refreshToken);
        assertFalse(refreshToken.isBlank());
    }

    @Test
    void shouldExtractUsername() {
        String accessToken = jwtUtil.generateAccessToken(testEmail);
        String username = jwtUtil.extractUsername(accessToken);

        assertEquals(testEmail, username);
    }

    @Test
    void shouldValidateToken() {
        String accessToken = jwtUtil.generateAccessToken(testEmail);

        assertTrue(jwtUtil.isTokenValid(accessToken));
    }
}