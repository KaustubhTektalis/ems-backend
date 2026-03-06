package com.ems.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDTO{

    private String empId;
    private String name;
    private String companyEmail;
    private String department;
    private String designation;
    private LocalDate dateOfJoin;
}