package com.employee.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeesDetailsDTO {

	private String empId;
	private String name;
	private String companyMail;
	private String department;
	private String designation;
	private String personalMail;
	private String address;
	private String phoneNumber;
	private LocalDate dateOfJoin;
}