package com.employee.services;

import com.employee.dto.ChangePasswordRequest;
import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RefreshTokenRequest;
import com.employee.dto.ResetPasswordRequest;

public interface AuthService {

	LoginResponse login(LoginRequest request);

	LoginResponse refreshToken(RefreshTokenRequest request);
	void changePassword(String empId, ChangePasswordRequest request);
	void forgetPassword(String email);
	void resetPassword(ResetPasswordRequest request);

}