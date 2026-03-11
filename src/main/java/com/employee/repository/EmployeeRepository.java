package com.employee.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.entity.Employee;
 
public interface EmployeeRepository  extends JpaRepository<Employee,String>{
	  @Query(value="Select nextval('emp_id_seq')", nativeQuery=true)
	    Long getNextEmployeeId();
 
}