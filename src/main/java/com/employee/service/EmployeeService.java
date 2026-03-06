package com.employee.service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.employee.dto.CreateEmployeeDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.entity.Employee;
import com.employee.entity.User;
import com.employee.entity.Roles;
import com.employee.entity.SetCompKey;
import com.employee.entity.UserRoles;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.PasswordRepository;
import com.employee.repository.RolesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;
    private final PasswordRepository passwordRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public EmployeeResponseDTO createEmployee(CreateEmployeeDTO dto) {
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


        User password = User.builder()
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

        return employeeDetails(savedEmployee);
    }

    public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findByIsEmployeeActiveTrue(pageable);
    }
    

    public Page<EmployeeResponseDTO> getEmployeesByName(String name, Pageable pageable) {
        return employeeRepository.findByNameContainingIgnoreCaseAndIsEmployeeActiveTrue(name, pageable);
    }
     

    public Page<EmployeeResponseDTO> getEmployeesByDepartment(String department, Pageable pageable) {
        return employeeRepository.findByDepartmentIgnoreCaseAndIsEmployeeActiveTrue(department, pageable);
    }
     

    public Page<EmployeeResponseDTO> getEmployeesByJoiningDate(LocalDate date, Pageable pageable) {
        return employeeRepository.findByDateOfJoinGreaterThanEqualAndIsEmployeeActiveTrue(date, pageable);
    }
   

    public EmployeeResponseDTO getEmployeeById(String empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + empId));
     
        if (!Boolean.TRUE.equals(employee.getIsEmployeeActive())) {
            throw new EntityNotFoundException("Employee is not active");
        }
        return employeeDetails(employee);
    }


    public void deleteEmployee(String empId) {

    	Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new EntityNotFoundException("Employee with id " + empId + " not found"));

    	employee.setIsEmployeeActive(false);
		employee.setDateOfExit(LocalDate.now());
		employeeRepository.save(employee);
    }

    
    private EmployeeResponseDTO employeeDetails(Employee employee) {
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