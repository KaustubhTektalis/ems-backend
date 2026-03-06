package com.ems.dto;

import java.time.LocalDate;
import java.util.Set;

import com.ems.enums.RolesEnum;
import lombok.Data;

@Data
public class CreateEmployeeRequestDTO {

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