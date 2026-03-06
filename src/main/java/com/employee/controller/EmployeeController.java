package com.employee.controller;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.employee.dto.CreateEmployeeDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public EmployeeResponseDTO addEmployee(
            @RequestBody CreateEmployeeDTO request) {

        return employeeService.createEmployee(request);
    }

    @GetMapping("/employees")
    public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
        return employeeService.getAllEmployees(pageable);
    }

    @GetMapping("/id/{empId}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable String empId) {
        return employeeService.getEmployeeById(empId);
    }
    
    @GetMapping("/name/{name}")
    public Page<EmployeeResponseDTO> getEmployeesByName(@PathVariable String name, Pageable pageable) {
        return employeeService.getEmployeesByName(name, pageable);
    }
    @GetMapping("/department/{department}")
    public Page<EmployeeResponseDTO> getEmployeesByDepartment(@PathVariable String department, Pageable pageable) {
        return employeeService.getEmployeesByDepartment(department, pageable);
    }
    @GetMapping("/date/{date}")
    public Page<EmployeeResponseDTO> getEmployeesByJoiningDate(@PathVariable LocalDate date, Pageable pageable) {
        return employeeService.getEmployeesByJoiningDate(date, pageable);
    }

    @DeleteMapping("/employee/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String empId) {

        employeeService.deleteEmployee(empId);
        return ResponseEntity.ok("Employee soft deleted successfully");
    }
}