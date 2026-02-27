
package com.employee.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.employee.entity.UserRole;
import com.employee.entity.UserRoleId;
 
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId>{
 
}