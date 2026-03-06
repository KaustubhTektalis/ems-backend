package com.employee.repository;
 
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.dto.EmployeeResponseDTO;
import com.employee.entity.Employee;


@Repository 
public interface EmployeeRepository extends JpaRepository<Employee,String>{
 
    Page<EmployeeResponseDTO> findByIsEmployeeActiveTrue(Pageable pageable);
 
    Page<EmployeeResponseDTO> findByNameContainingIgnoreCaseAndIsEmployeeActiveTrue(String name, Pageable pageable);
 
    Page<EmployeeResponseDTO> findByDepartmentIgnoreCaseAndIsEmployeeActiveTrue(String department, Pageable pageable);
 
    Page<EmployeeResponseDTO> findByDateOfJoinGreaterThanEqualAndIsEmployeeActiveTrue(LocalDate date, Pageable pageable);
}