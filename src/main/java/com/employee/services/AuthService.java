package com.employee.services;

import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RefreshTokenRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);
    
}