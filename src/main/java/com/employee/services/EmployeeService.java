package com.employee.services;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.employee.dto.CreateEmployeeDTO;
import com.employee.dto.EmployeesDetailsDTO;
import com.employee.dto.UpdateAllDTO;

public interface EmployeeService {
	
	public EmployeesDetailsDTO createEmployee(CreateEmployeeDTO dto);
	
	public Page<EmployeesDetailsDTO> getAllEmployees(Pageable pageable);
	public Page<EmployeesDetailsDTO> getEmployeesByName(String name, Pageable pageable);
	public Page<EmployeesDetailsDTO> getEmployeesByDepartment(String department, Pageable pageable);
	public Page<EmployeesDetailsDTO> getEmployeesByJoiningDate(LocalDate date, Pageable pageable);
	public EmployeesDetailsDTO getEmployeeById(String empId);
	public Page<EmployeesDetailsDTO> getInactiveEmployees(Pageable pageable);
	public void deleteEmployee(String empId);
	public EmployeesDetailsDTO updateAll(String empId, UpdateAllDTO dto);
	public EmployeesDetailsDTO updateName(String empId, String name);
	public EmployeesDetailsDTO updatePersonalMail(String empId, String personalMail);
	public EmployeesDetailsDTO updatePhoneNumber(String empId, String phoneNumber);
	public EmployeesDetailsDTO updateAddress(String empId, String address);
	

}
