package com.employee.repository;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.employee.entity.Users;
 
public interface UserRepository extends JpaRepository<Users,String> {
  Optional<Users>findByEmployeeCompanyEmail(String email);
}