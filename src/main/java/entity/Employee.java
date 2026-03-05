package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "employee")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID) // write a class for this.
	@Column(name = "emp_id", nullable = false, updatable = false)
	private String empId;

	@Column(nullable = false)
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
	private boolean isEmployeeActive = true;

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
}