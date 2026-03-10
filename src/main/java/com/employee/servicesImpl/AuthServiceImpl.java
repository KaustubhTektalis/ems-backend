package com.employee.servicesImpl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.employee.config.JwtUtils;
import com.employee.config.LoginDetails;
import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RefreshTokenRequest;
import com.employee.entity.Users;
import com.employee.repository.UserRepository;
import com.employee.repository.UserRoleRepository;
import com.employee.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	private final AuthenticationManager authenticationManager;
	private final UserRepository usersRepository;
	private final JwtUtils jwtUtils;
	private final UserRoleRepository userRoleRepository;

	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils,UserRoleRepository userRoleRepository,
			UserRepository usersRepository) {

		this.usersRepository = usersRepository;
		this.userRoleRepository=userRoleRepository;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@Override
	public LoginResponse login(LoginRequest request) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		LoginDetails loginDetails = (LoginDetails) authentication.getPrincipal();

		Users user = usersRepository.findById(loginDetails.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		String accessToken = jwtUtils.generateAccessToken(loginDetails.getUsername());

		String refreshToken = jwtUtils.generateRefreshToken(loginDetails.getUsername());

		List<String> roles = loginDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		return LoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
				.empId(loginDetails.getUsername()).companyEmail(user.getEmployee().getCompanyEmail()).roles(roles)
				.build();
	}

	@Override
	public LoginResponse refreshToken(RefreshTokenRequest request) {

		String username = jwtUtils.extractUserName(request.getRefreshToken());

		String newAccessToken = jwtUtils.generateAccessToken(username);

		Users user = usersRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));

		List<String> roles = userRoleRepository.findByEmployeeEmpId(username).stream()
				.map(r -> r.getRole().getRole().name()).toList();

		return LoginResponse.builder().accessToken(newAccessToken).refreshToken(request.getRefreshToken())
				.companyEmail(user.getEmployee().getCompanyEmail()).roles(roles).empId(username).build();
	}
}
