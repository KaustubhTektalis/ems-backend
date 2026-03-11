package com.employee.controllers;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.employee.dto.CreateEmployeeDTO;
import com.employee.dto.EmployeesDetailsDTO;
import com.employee.dto.UpdateAllDTO;
import com.employee.servicesImpl.EmployeeServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {

	private final EmployeeServiceImpl employeeService;

	@PostMapping("/employee")
	public EmployeesDetailsDTO addEmployee(@RequestBody CreateEmployeeDTO request) {

		return employeeService.createEmployee(request);
	}

	@GetMapping("/employees")
	public Page<EmployeesDetailsDTO> getAllEmployees(Pageable pageable) {
		return employeeService.getAllEmployees(pageable);
	}

	@GetMapping("/id/{empId}")
	public EmployeesDetailsDTO getEmployeeById(@PathVariable String empId) {
		return employeeService.getEmployeeById(empId);
	}

	@GetMapping("/name/{name}")
	public Page<EmployeesDetailsDTO> getEmployeesByName(@PathVariable String name, Pageable pageable) {
		return employeeService.getEmployeesByName(name, pageable);
	}

	@GetMapping("/department/{department}")
	public Page<EmployeesDetailsDTO> getEmployeesByDepartment(@PathVariable String department, Pageable pageable) {
		return employeeService.getEmployeesByDepartment(department, pageable);
	}

	@GetMapping("/date/{date}")
	public Page<EmployeesDetailsDTO> getEmployeesByJoiningDate(@PathVariable LocalDate date, Pageable pageable) {
		return employeeService.getEmployeesByJoiningDate(date, pageable);
	}

	@DeleteMapping("/employee/{empId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable String empId) {

		employeeService.deleteEmployee(empId);
		return ResponseEntity.ok("Employee soft deleted successfully");
	}

	@PutMapping("/update/all/{empId}")
	public EmployeesDetailsDTO updateAll(@PathVariable String empId, @RequestBody UpdateAllDTO dto) {
		return employeeService.updateAll(empId, dto);
	}

	@PatchMapping("/update/name/{empId}")
	public EmployeesDetailsDTO updateName(@PathVariable String empId, @RequestParam String name) {
		return employeeService.updateName(empId, name);
	}

	@PatchMapping("/update/mail/{empId}")
	public EmployeesDetailsDTO updatePersonalMail(@PathVariable String empId, @RequestParam String personalMail) {
		return employeeService.updatePersonalMail(empId, personalMail);
	}

	@PatchMapping("/update/phone/{empId}")
	public EmployeesDetailsDTO updatePhoneNumber(@PathVariable String empId, @RequestParam String phoneNumber) {
		return employeeService.updatePhoneNumber(empId, phoneNumber);
	}

	@PatchMapping("/update/address/{empId}")
	public EmployeesDetailsDTO updateAddress(@PathVariable String empId, @RequestParam String address) {
		return employeeService.updateAddress(empId, address);
	}
}