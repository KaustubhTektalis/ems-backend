package services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import entity.Employee;
import repository.EmployeeRepo;

@Service
public class EmployeeService {
    
    private final EmployeeRepo employeeRepository;
    
    public EmployeeService(EmployeeRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    public void createEmployee() {
        Employee emp = Employee.builder()
            .name("John Doe")
            .companyEmail("john@company.com")
            .personalEmail("john@gmail.com")
            .phoneNumber("1234567890")
            .address("123 Street")
            .department("Development")
            .designation("Frontend Dev")
            .dateOfJoin(LocalDate.of(2024, 1, 15))
            .dateOfBirth(LocalDate.of(1990, 5, 20))
            .build();
        
        employeeRepository.save(emp);
        System.out.println(emp.getEmpId());
    }
}