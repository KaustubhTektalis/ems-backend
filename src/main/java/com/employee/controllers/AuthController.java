package com.employee.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RefreshTokenRequest;
import com.employee.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse response = authService.login(request);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {

		LoginResponse response = authService.refreshToken(request);

		return ResponseEntity.ok(response);
	}
}
