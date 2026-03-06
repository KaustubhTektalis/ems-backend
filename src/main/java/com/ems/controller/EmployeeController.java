package com.ems.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ems.dto.CreateEmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public EmployeeResponseDTO addEmployee(
            @RequestBody CreateEmployeeRequestDTO request) {

        return employeeService.createEmployee(request);
    }

    @GetMapping("/employees")
    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{empId}")
    public EmployeeResponseDTO getEmployeeById(
            @PathVariable String empId) {

        return employeeService.getEmployeeById(empId);
    }

    @DeleteMapping("/{empId}")
    public String deleteEmployee(@PathVariable String empId) {

        employeeService.deleteEmployee(empId);
        return "Employee deleted successfully";
    }
}