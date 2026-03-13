package com.employee.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.employee.dto.ChangePasswordRequest;
import com.employee.dto.ForgetPasswordRequest;
import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RefreshTokenRequest;
import com.employee.dto.ResetPasswordRequest;
import com.employee.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

		LoginResponse response = authService.login(request);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) {

		LoginResponse response = authService.refreshToken(request);

		return ResponseEntity.ok(response);
	}
	@PutMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
		String empId=SecurityContextHolder.getContext().getAuthentication().getName();
		authService.changePassword(empId, request);
		return ResponseEntity.ok("Password Changed Successfully ");
	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordRequest request){
		authService.forgetPassword(request.getEmail());
		return ResponseEntity.ok("Reset Link is Sent to Email ");
	}
	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
		authService.resetPassword(request);
		return ResponseEntity.ok("Password Reset Successfully");
	}
	

}