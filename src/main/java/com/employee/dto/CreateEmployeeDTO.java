package com.employee.dto;

import java.time.LocalDate;
import java.util.Set;

import com.employee.enums.RolesEnum;

import lombok.Data;

@Data
public class CreateEmployeeDTO {

    private String name;
    private String companyEmail;
    private String personalEmail;
    private String phoneNumber;
    private String address;
    private String department;
    private String designation;
    private LocalDate dateOfJoin;
    private LocalDate dateOfBirth;
    private String description;

    private Set<RolesEnum> roles;
}