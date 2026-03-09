package com.employee.config;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.employee.entity.Users;
import com.employee.entity.Role;
import com.employee.repository.UserRepository;
import com.employee.repository.UserRoleRepository;

public class LoginDetailService implements UserDetailsService {
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;

	public LoginDetailService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public LoginDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user;
		if (username.contains("@")) {
			user = userRepository.findByEmployeeCompanyEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		} else {
			user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

		}
		var roles = userRoleRepository.findByEmployeeEmpId(user.getEmpId());

		List<SimpleGrantedAuthority> authorities = roles.stream()
			    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().getRole().name()))
			    .toList();


		return new LoginDetails(user, authorities);
	}

}
