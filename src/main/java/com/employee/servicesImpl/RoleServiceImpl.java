package com.employee.servicesImpl;

import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.entity.Role;
import com.employee.entity.UserRole;
import com.employee.enums.RolesEnum;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.RoleRepository;
import com.employee.repository.UserRoleRepository;
import com.employee.services.RoleService;
import com.employee.util.UserRoleId;

import jakarta.transaction.Transactional;
@Service
public class RoleServiceImpl implements RoleService{
	private  final RoleRepository roleRepository;
	private  final UserRoleRepository userRoleRepository;
	private final EmployeeRepository employeeRepository;
	public RoleServiceImpl(RoleRepository roleRepository,UserRoleRepository userRoleRepository, EmployeeRepository employeeRepository) {
		this.roleRepository=roleRepository;
		this.userRoleRepository=userRoleRepository;
		this.employeeRepository=employeeRepository;
	}
	@Override
	@Transactional
	public void assignRole(String empId, String roleName) {

	    Role role = roleRepository
	            .findByRole(RolesEnum.valueOf(roleName))
	            .orElseThrow(() -> new RuntimeException("Role not found"));

	    Employee employee = employeeRepository
	            .findById(empId)
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    UserRoleId id = new UserRoleId(empId, role.getRoleId());

	    if (userRoleRepository.existsById(id)) {
	        throw new RuntimeException("Role is already assigned");
	    }

	    UserRole userRole = new UserRole();
	    userRole.setId(id);
	    userRole.setEmployee(employee);  
	    userRole.setRole(role);

	    userRoleRepository.save(userRole);
	}
	
	@Override
	@Transactional
	public void removeRole(String empId, String roleName) {

	    Role role = roleRepository
	            .findByRole(RolesEnum.valueOf(roleName))
	            .orElseThrow(() -> new RuntimeException("Role not found"));

	    Employee employee = employeeRepository
	            .findById(empId)
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    UserRoleId id = new UserRoleId(empId, role.getRoleId());

	    UserRole userRole = userRoleRepository
	            .findById(id)
	            .orElseThrow(() -> new RuntimeException("Role is not assigned"));

	    userRoleRepository.delete(userRole);
	}
	

}
