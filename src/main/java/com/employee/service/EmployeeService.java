package com.employee.service;

import java.util.List;

import com.employee.dto.CreateEmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO request);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getEmployeeById(String empId);

    void deleteEmployee(String empId);

}