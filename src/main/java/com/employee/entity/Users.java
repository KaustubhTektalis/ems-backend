package com.employee.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

	@Id
	@Column(name = "emp_id", nullable = false, updatable = false)
	private String empId;

	@Column(name = "password", nullable = false)
	private String password;

	@Builder.Default
	@Column(name = "is_user_active", nullable = false)
	private Boolean isEmployeeActive = true;

	@Column(name = "password_changed_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "emp_id")
	private Employee employee;
}