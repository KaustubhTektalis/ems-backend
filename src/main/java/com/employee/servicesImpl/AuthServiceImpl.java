package com.employee.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.config.JwtUtils;
import com.employee.dto.ChangePasswordRequest;
import com.employee.dto.LoginRequest;
import com.employee.dto.LoginResponse;
import com.employee.dto.RefreshTokenRequest;
import com.employee.dto.ResetPasswordRequest;
import com.employee.entity.PasswordResetToken;
import com.employee.entity.Users;
import com.employee.repository.PasswordResetTokenRepository;
import com.employee.repository.UserRepository;
import com.employee.repository.UserRoleRepository;
import com.employee.services.AuthService;
import com.employee.services.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final PasswordResetTokenRepository tokenRepository;
	private final EmailService emailService;
	



	@Override
	public LoginResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		Users user;

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

		Users user = userRepository.findById(empId).orElseThrow(() -> new RuntimeException("User not found"));

		if (!jwtUtils.validateToken(refreshToken, empId)) {
			throw new RuntimeException("Invalid refresh token");
		}

		List<String> roles = userRoleRepository.findByEmployeeEmpId(empId).stream()
				.map(r -> r.getRole().getRole().name()).toList();

		String newAccessToken = jwtUtils.generateAccessToken(empId, roles);

		return LoginResponse.builder().token(newAccessToken).refreshToken(refreshToken).empId(empId)
				.companyEmail(user.getEmployee().getCompanyEmail()).roles(roles).build();
	}
	
	@Override
	public void changePassword(String empId, ChangePasswordRequest request) {

	    Users user = userRepository.findById(empId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
	        throw new RuntimeException("Old password incorrect");
	    }

	    user.setPassword(passwordEncoder.encode(request.getNewPassword()));

	    userRepository.save(user);
	}

	@Override
	public void forgetPassword(String email) {
		Users user=userRepository.findByEmployeeCompanyEmail(email)
				.orElseThrow(()->new RuntimeException("User not Found"));
		
		String token=UUID.randomUUID().toString();
		PasswordResetToken resetToken=new PasswordResetToken();
		resetToken.setToken(token);
	    resetToken.setEmpId(user.getEmpId());
	    resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
	    tokenRepository.save(resetToken);
	    String resetLink =
	            "http://localhost:3000/resetPassword?token=" + token;

	    emailService.sendResetPasswordEmail(email, resetLink);
		
	}

	@Override
	public void resetPassword(ResetPasswordRequest request) {
		PasswordResetToken token=tokenRepository.findByToken(request.getToken())
				.orElseThrow(()->new RuntimeException("Invalid Token"));
		if(token.getExpiryDate().isEqual(LocalDateTime.now())) {
			throw new RuntimeException("Token expired");
		}
		Users user=userRepository.findById(token.getEmpId())
				.orElseThrow(()-> new RuntimeException("User not found"));
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
		tokenRepository.delete(token);
	}
}