package com.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @GeneratedValue(generator = "emp-id-generator")
    @GenericGenerator(
        name = "emp-id-generator",
        strategy = "com.employee.utils.EmployeeIdGenerator"
    )
    @EqualsAndHashCode.Include
    @Column(name = "emp_id")
    private String empId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "company_email", nullable = false, unique = true, updatable = false)
	private String companyEmail;

	@Column(name = "personal_email", nullable = false, unique = true)
	private String personalEmail;

	@Column(name = "phone_number", nullable = false, unique = true)
	private String phoneNumber;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "department", nullable = false)
	private String department;

	@Column(name = "designation", nullable = false)
	private String designation; // ex: dept: development, designation: frontend dev

	@Column(name = "date_of_join", nullable = false)
	private LocalDate dateOfJoin;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "date_of_exit", nullable = true)
	private LocalDate dateOfExit;

	@Builder.Default
	@Column(name = "employee_active", nullable = false)
	private Boolean isEmployeeActive = true;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserRoles> roles;
}
