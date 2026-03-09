package com.employee.repository;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.entity.Role;
import com.employee.entity.RoleName;
 
public interface RoleRepository extends JpaRepository<Role,Integer>{
 Optional<Role>findByRole(RoleName role);
}
