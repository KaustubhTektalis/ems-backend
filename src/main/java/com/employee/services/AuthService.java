package com.employee.services;

import com.employee.dto.LoginRequest;
import com.employee.dto.RefreshTokenRequest;

public interface  AuthService {
	void LoginResponse(LoginRequest request);
	void LoginResponse(RefreshTokenRequest request);

}
