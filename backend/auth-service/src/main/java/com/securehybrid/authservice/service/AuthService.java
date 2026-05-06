package com.securehybrid.authservice.service;

import com.securehybrid.authservice.dto.request.LoginRequest;
import com.securehybrid.authservice.dto.request.RegisterRequest;
import com.securehybrid.authservice.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
