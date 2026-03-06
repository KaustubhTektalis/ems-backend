package com.employee.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.dto.CreateEmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.entity.Employee;
import com.employee.entity.Password;
import com.employee.entity.Roles;
import com.employee.entity.SetCompKey;
import com.employee.entity.UserRoles;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.PasswordRepository;
import com.employee.repository.RolesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;
    private final PasswordRepository passwordRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO dto) {

    	Employee employee = Employee.builder()
                .name(dto.getName())
                .companyEmail(dto.getCompanyEmail())
                .personalEmail(dto.getPersonalEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .dateOfJoin(dto.getDateOfJoin())
                .dateOfBirth(dto.getDateOfBirth())
                .description(dto.getDescription())
                .build();

    	Employee savedEmployee = employeeRepository.save(employee);


        Password password = Password.builder()
                .employee(savedEmployee)
                .password("pass")
                .build();

        passwordRepository.save(password);

        Set<UserRoles> userRoles = dto.getRoles().stream()
                .map(roleEnum -> {

                    Roles role = rolesRepository.findByRole(roleEnum);

                    return UserRoles.builder()
                            .employee(savedEmployee)
                            .role(role)
                            .comp(new SetCompKey(savedEmployee.getEmpId(), role.getRoleId()))
                            .build();
                })
                .collect(Collectors.toSet());

        employee.setRoles(userRoles);

        return buildResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String empId) {

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return buildResponse(employee);
    }

    @Override
    public void deleteEmployee(String empId) {

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.delete(employee);
    }

    private EmployeeResponseDTO buildResponse(Employee employee) {
        return EmployeeResponseDTO.builder()
        		.empId(employee.getEmpId())
                .name(employee.getName())
                .companyEmail(employee.getCompanyEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .dateOfJoin(employee.getDateOfJoin())
                .build();
    }
}