package com.employee.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "passwords")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Password {

	@Id
	@Column(name = "emp_id", nullable = false, updatable = false)
	private String empId;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "emp_id")
	private Employee employee;
	

	@Column(name = "password", nullable = false)
	private String password;

	@Builder.Default
	@Column(name = "employee_active", nullable = false)
	private Boolean isEmployeeActive = true;

	@Column(name = "password_created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "password_updated_at")
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