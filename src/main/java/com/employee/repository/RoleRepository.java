package com.employee.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.employee.entity.Role;
 
public interface RoleRepository extends JpaRepository<Role,Integer>{
 
}
