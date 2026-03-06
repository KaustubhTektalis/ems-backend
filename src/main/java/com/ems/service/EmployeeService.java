package com.ems.service;

import java.util.List;

import com.ems.dto.CreateEmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO request);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getEmployeeById(String empId);

    void deleteEmployee(String empId);

}