package com.employee.servicesImpl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.employee.config.JwtUtils;
import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RefreshTokenRequest;
import com.employee.entity.User;
import com.employee.repository.UserRepository;
import com.employee.repository.UserRoleRepository;
import com.employee.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public AuthServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
			AuthenticationManager authenticationManager, JwtUtils jwtUtils) {

		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@Override
	public LoginResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		User user;

		if (request.getUsername().contains("@")) {

			user = userRepository.findByEmployeeCompanyEmail(request.getUsername())
					.orElseThrow(() -> new RuntimeException("User not found"));

		} else {

			user = userRepository.findById(request.getUsername())
					.orElseThrow(() -> new RuntimeException("User not found"));
		}

		List<String> roles = userRoleRepository.findByEmployeeEmpId(user.getEmpId()).stream()
				.map(r -> r.getRole().getRole().name()).toList();

		String accessToken = jwtUtils.generateAccessToken(user.getEmpId(), roles);

		String refreshToken = jwtUtils.generateRefreshToken(user.getEmpId());

		return LoginResponse.builder().token(accessToken).refreshToken(refreshToken).empId(user.getEmpId())
				.companyEmail(user.getEmployee().getCompanyEmail()).roles(roles).build();
	}

	@Override
	public LoginResponse refreshToken(RefreshTokenRequest request) {

		String refreshToken = request.getRefreshToken();

		String empId = jwtUtils.extractUsername(refreshToken);

		User user = userRepository.findById(empId).orElseThrow(() -> new RuntimeException("User not found"));

		if (!jwtUtils.validateToken(refreshToken, empId)) {
			throw new RuntimeException("Invalid refresh token");
		}

		List<String> roles = userRoleRepository.findByEmployeeEmpId(empId).stream()
				.map(r -> r.getRole().getRole().name()).toList();

		String newAccessToken = jwtUtils.generateAccessToken(empId, roles);

		return LoginResponse.builder().token(newAccessToken).refreshToken(refreshToken).empId(empId)
				.companyEmail(user.getEmployee().getCompanyEmail()).roles(roles).build();
	}
}