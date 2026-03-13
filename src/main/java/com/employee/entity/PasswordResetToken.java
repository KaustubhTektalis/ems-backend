package com.employee.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class PasswordResetToken {
	@Id
	@GeneratedValue
	 private Long id;
	private String token;
	private String empId;
	private LocalDateTime expiryDate;

	 
}
