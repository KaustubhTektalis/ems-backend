package com.employee.entity;
 
import java.time.LocalDateTime;
 
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Entity
 
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name="users")
public class Users {
	@Id
	@Column(name="emp_id")
	private String empId;
	//@Column(name="company_email",nullable=false,unique=true)
	//private String companyEmail;
	@Column(nullable=false)
	private String password;
	@Column(name="is_user_active")
	private boolean isUserActive=true;
	@Column(name="created_at",updatable=false)
	private LocalDateTime createdAt=LocalDateTime.now();
	@Column(name="password_changed_at",updatable=false)
	@UpdateTimestamp
	private LocalDateTime passwordChangedAt;
	//CreationTImeStamp
	//private LocalDateTime createdAt;
	@OneToOne(fetch =FetchType.LAZY)
	@MapsId
	@JoinColumn(name="emp_id")
	private Employee employee;
 
}
