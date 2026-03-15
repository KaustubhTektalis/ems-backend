package com.employee.servicesImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.entity.User;
import com.employee.exceptions.EmployeeNotFoundException;
import com.employee.repository.UserRepository;
import com.employee.services.EmailService;
import com.employee.services.UserService;
import com.employee.utils.GeneratePassword;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	@Transactional
	public void updatePassword(String empId, String updatedPassword) {
		User user = userRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("User not found"));
		user.setPassword(passwordEncoder.encode(updatedPassword));
	}

	@Transactional
	public void resetPassword(String empId) {
		User user = userRepository.findById(empId).orElseThrow(() -> new EntityNotFoundException("User not found"));
		Employee employee = user.getEmployee();
		String rawPassword = GeneratePassword.generatePassword(8);
		user.setPassword(passwordEncoder.encode(rawPassword));
		
		emailService.sendLoginDetails(employee.getPersonalEmail(), employee.getEmpId(),
				employee.getCompanyEmail(), rawPassword, employee.getName());
	}

}