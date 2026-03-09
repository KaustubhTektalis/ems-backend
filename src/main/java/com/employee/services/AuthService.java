package com.employee.services;

import com.employee.dto.LoginRequest;
import com.employee.dto.RefreshTokenRequest;

public interface  AuthService {
	void loginResponse(LoginRequest request);
	void loginResponse(RefreshTokenRequest request);

}
