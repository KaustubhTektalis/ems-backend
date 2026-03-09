package com.employee.repository;
 
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.dto.EmployeesDetailsDTO;
import com.employee.entity.Employee;


@Repository 
public interface EmployeeRepository extends JpaRepository<Employee,String>{
 
	EmployeesDetailsDTO findByEmpIdAndIsEmployeeActiveTrue(String empId);
	
    Page<EmployeesDetailsDTO> findByIsEmployeeActiveTrue(Pageable pageable);
    
    Page<EmployeesDetailsDTO> findByIsEmployeeActiveFalse(Pageable pageable);
 
    Page<EmployeesDetailsDTO> findByNameContainingIgnoreCaseAndIsEmployeeActiveTrue(String name, Pageable pageable);
 
    Page<EmployeesDetailsDTO> findByDepartmentIgnoreCaseAndIsEmployeeActiveTrue(String department, Pageable pageable);
 
    Page<EmployeesDetailsDTO> findByDateOfJoinGreaterThanEqualAndIsEmployeeActiveTrue(LocalDate date, Pageable pageable);
    
    Page<EmployeesDetailsDTO> findByName(LocalDate data, Pageable page);
    
//    void updateIsEmployeeActiveFalseAndSetDateOfExit(String empId);
}