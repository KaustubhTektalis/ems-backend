package com.employee.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "audits")
public class AuditLog {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sNo;
	
	@Column(name = "user", nullable = false)
	private String user;

	@Column(name = "operation", nullable = false)
	private String operation;

	@Column(name = "target", nullable = false)
	private String target;

	@CreationTimestamp
	@Column(name="performed_at",updatable=false)
	private LocalDateTime performedAt;

}
