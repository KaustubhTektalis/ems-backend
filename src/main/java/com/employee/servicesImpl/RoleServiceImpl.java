package com.employee.servicesImpl;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.entity.Roles;
import com.employee.entity.UserRoles;
import com.employee.enums.RolesEnum;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.RoleRepository;
//import com.employee.repository.UserRepository;
import com.employee.repository.UserRoleRepository;
import com.employee.services.RoleService;
import com.employee.utils.UserRoleId;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final EmployeeRepository employeeRepository;
	@Override
	@Transactional
	public void assignRole(String empId, String roleName) {

		Roles role = roleRepository.findByRole(RolesEnum.valueOf(roleName))
				.orElseThrow(() -> new RuntimeException("Role not found"));

		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		UserRoleId id = new UserRoleId(empId, role.getRoleId());

		if (userRoleRepository.existsById(id)) {
			throw new RuntimeException("Role is already assigned");
		}

		UserRoles userRole = new UserRoles();
		userRole.setId(id);
		userRole.setEmployee(employee);
		userRole.setRole(role);

		userRoleRepository.save(userRole);
	}

	@Override
	@Transactional
	public void removeRole(String empId, String roleName) {

		Roles role = roleRepository.findByRole(RolesEnum.valueOf(roleName))
				.orElseThrow(() -> new RuntimeException("Role not found"));

		UserRoleId id = new UserRoleId(empId, role.getRoleId());

		UserRoles userRole = userRoleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Role is not assigned"));

		userRoleRepository.delete(userRole);
	}

}
