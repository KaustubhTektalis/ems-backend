package com.employee.servicesImpl;

import org.springframework.stereotype.Service;

import com.employee.entity.Role;
import com.employee.entity.RoleName;
import com.employee.entity.UserRole;
import com.employee.entity.UserRoleId;
import com.employee.repository.RoleRepository;
import com.employee.repository.UserRoleRepository;
import com.employee.services.RoleService;

import jakarta.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService{
	private  final RoleRepository roleRepository;
	private  final UserRoleRepository userRoleRepository;
	public RoleServiceImpl(RoleRepository roleRepository,UserRoleRepository userRoleRepository) {
		this.roleRepository=roleRepository;
		this.userRoleRepository=userRoleRepository;
	}
	@Override
	@Transactional
	public void assignRole(String empId, String roleName) {
		Role role=roleRepository.findByRole(RoleName.valueOf( roleName)).orElseThrow(()->new RuntimeException("Role not found"));
		UserRoleId id =new UserRoleId(empId,role.getRoleId());
		if(userRoleRepository.existsById(id)) {
			throw new RuntimeException("Role is already Assigned ");
			}
			UserRole userRole=new UserRole();
			userRole.setId(id);
			userRole.setRole(role);
			userRoleRepository.save(userRole);
		}
		
	
	@Override
	@Transactional
	public void removeRole(String empId, String roleName) {
		Role role=roleRepository.findByRole(RoleName.valueOf( roleName)).orElseThrow(()->new RuntimeException("Role not found"));
		UserRoleId id =new UserRoleId(empId,role.getRoleId());
		if(!userRoleRepository.existsById(id)) {
			throw new RuntimeException("Role is not  Assigned ");
			}
			
			userRoleRepository.deleteById(id);
		
		
	}
	

}
