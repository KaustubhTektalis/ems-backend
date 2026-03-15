package com.employee.servicesImpl;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employee.dto.CreateEmployeeDTO;
import com.employee.dto.EmployeesDetailsDTO;
import com.employee.dto.UpdateAllDTO;
import com.employee.entity.Employee;
import com.employee.entity.User;
import com.employee.exceptions.DuplicateEmployeeException;
import com.employee.exceptions.EmployeeNotFoundException;
import com.employee.exceptions.InactiveEmployeeException;
import com.employee.entity.Roles;
import com.employee.entity.UserRoles;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.UserRepository;
import com.employee.repository.RoleRepository;
import com.employee.services.EmailService;
import com.employee.services.EmployeeService;
import com.employee.utils.GeneratePassword;
import com.employee.utils.UserRoleId;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public EmployeesDetailsDTO createEmployee(CreateEmployeeDTO dto) {

		if (employeeRepository.existsByCompanyEmail(dto.getCompanyEmail())) {
			throw new DuplicateEmployeeException("Employee already exists with email: " + dto.getCompanyEmail());
		}
		if (employeeRepository.existsByPersonalEmail(dto.getPersonalEmail())) {
			throw new DuplicateEmployeeException("Email already exists" + dto.getCompanyEmail());
		}
		if (employeeRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
			throw new DuplicateEmployeeException("Phone number already exists" + dto.getPhoneNumber());
		}

		Employee employee = Employee.builder().name(dto.getName()).companyEmail(dto.getCompanyEmail())
				.personalEmail(dto.getPersonalEmail()).phoneNumber(dto.getPhoneNumber()).address(dto.getAddress())
				.department(dto.getDepartment()).designation(dto.getDesignation()).dateOfJoin(dto.getDateOfJoin())
				.dateOfBirth(dto.getDateOfBirth()).description(dto.getDescription()).build();

		Employee savedEmployee = employeeRepository.save(employee);

		String rawPassword = GeneratePassword.generatePassword(8);

		User user = User.builder().employee(savedEmployee).password(passwordEncoder.encode(rawPassword)).build();

		userRepository.save(user);

		Set<UserRoles> userRoles = dto.getRoles().stream().map(roleEnum -> {

			Roles role = roleRepository.findByRole(roleEnum)
					.orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleEnum));

			return UserRoles.builder().employee(savedEmployee).role(role)
					.id(new UserRoleId(savedEmployee.getEmpId(), role.getRoleId())).build();
		}).collect(Collectors.toSet());

		employee.setRoles(userRoles);
		emailService.sendLoginDetails(savedEmployee.getPersonalEmail(), savedEmployee.getEmpId(),
				savedEmployee.getCompanyEmail(), rawPassword, savedEmployee.getName());

		return employeeDetails(savedEmployee);
	}

	// --------------------------------------------------------------------------------------------------------

	public Page<EmployeesDetailsDTO> getAllEmployees(Pageable pageable) {
		return employeeRepository.findByIsEmployeeActiveTrue(pageable);
	}

	public Page<EmployeesDetailsDTO> getEmployeesByName(String name, Pageable pageable) {
		return employeeRepository.findByNameContainingIgnoreCaseAndIsEmployeeActiveTrue(name, pageable);
	}

	public Page<EmployeesDetailsDTO> getEmployeesByDepartment(String department, Pageable pageable) {
		return employeeRepository.findByDepartmentIgnoreCaseAndIsEmployeeActiveTrue(department, pageable);
	}

	public Page<EmployeesDetailsDTO> getEmployeesByJoiningDate(LocalDate date, Pageable pageable) {
		return employeeRepository.findByDateOfJoinGreaterThanEqualAndIsEmployeeActiveTrue(date, pageable);
	}

	public EmployeesDetailsDTO getEmployeeById(String empId) {
		EmployeesDetailsDTO employee = employeeRepository.findByEmpIdAndIsEmployeeActiveTrue(empId);
		if (employee == null) {
			throw new EntityNotFoundException("Employee not found with id: " + empId);
		}
		return employee;
	}

	public Page<EmployeesDetailsDTO> getInactiveEmployees(Pageable pageable) {
		return employeeRepository.findByIsEmployeeActiveFalse(pageable);
	}

	// --------------------------------------------------------------------------------------------------------

	@Transactional
	public void deleteEmployee(String empId) {

		Employee employee = getActiveEmployee(empId);
		employee.setIsEmployeeActive(false);
		employee.setDateOfExit(LocalDate.now());
	}

	// ------------------------------------------------------------------------------------------------------

	@Transactional
	public EmployeesDetailsDTO updateAll(String empId, UpdateAllDTO dto) {
		Employee employee = getActiveEmployee(empId);

		employee.setName(dto.getName());
		employee.setPhoneNumber(dto.getPhoneNumber());
		employee.setAddress(dto.getAddress());
		employee.setPersonalEmail(dto.getPersonalEmail());

		return employeeDetails(employee);
	}

	@Transactional
	public EmployeesDetailsDTO updateName(String empId, String name) {
		Employee employee = getActiveEmployee(empId);

		employee.setName(name);
		return employeeDetails(employee);
	}

	@Transactional
	public EmployeesDetailsDTO updatePersonalEmail(String empId, String personalEmail) {
		Employee employee = getActiveEmployee(empId);

		employee.setPersonalEmail(personalEmail);
		return employeeDetails(employee);
	}

	@Transactional
	public EmployeesDetailsDTO updatePhoneNumber(String empId, String phoneNumber) {
		Employee employee = getActiveEmployee(empId);

		employee.setPhoneNumber(phoneNumber);
		return employeeDetails(employee);
	}

	@Transactional
	public EmployeesDetailsDTO updateAddress(String empId, String address) {
		Employee employee = getActiveEmployee(empId);

		employee.setAddress(address);
		return employeeDetails(employee);
	}

	// ------------------------------------------------------------------------------------------------------

	// Helper
	private Employee getActiveEmployee(String empId) {
		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + empId));

		if (!employee.getIsEmployeeActive()) {
			throw new InactiveEmployeeException("employee is inactive");
		}

		return employee;
	}

	// ------------------------------------------------------------------------------------------------------

	// Mapper
	private EmployeesDetailsDTO employeeDetails(Employee employee) {
		return EmployeesDetailsDTO.builder().empId(employee.getEmpId()).name(employee.getName())
				.companyEmail(employee.getCompanyEmail()).department(employee.getDepartment())
				.personalEmail(employee.getPersonalEmail()).address(employee.getAddress())
				.phoneNumber(employee.getPhoneNumber()).designation(employee.getDesignation())
				.dateOfJoin(employee.getDateOfJoin()).build();
	}
}