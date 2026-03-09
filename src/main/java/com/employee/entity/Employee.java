package com.employee.entity;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Entity

@Table(name="employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Employee {
	@Id
	@Column(name="emp_id",nullable=false)
	private String empId;
	@Column(nullable=false)
	private String name;
	@Column(name="company_email",nullable=false,unique = true,updatable=false)
	private String companyEmail;
	@Column(name="personal_email",nullable=false,unique = true)
	private String personalEmail;
	@Column(name="phone_number",nullable=false,unique = true)
	private String phoneNumber;
	@Column(nullable=false)
	private String address;
	@Column(nullable=false)
	private String department ;
	@Column(nullable=false)
	private String designation;
	@Column(name="date_of_join",nullable=false)
	private LocalDate dateOfJoin;
	@Column(name="date_of_birth",nullable=false)
	private LocalDate dateOfBirth;
	private String description;
	@Column(name="date_of_exit")
	private LocalDate dateOfExit;
	@Column(name="is_employee_active")
	private Boolean isEmployeeActive=true;
	@Column(name="created_at",updatable=false)
	private LocalDateTime createdAt=LocalDateTime.now();
	@Column(name="updated_at")
	private LocalDateTime updatedAt=LocalDateTime.now();

 
}
