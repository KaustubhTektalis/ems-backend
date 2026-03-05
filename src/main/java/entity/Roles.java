package entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

	@EmbeddedId
	private SetCompKey comp;

	@ManyToOne
	@MapsId("empId")
	@JoinColumn(name = "emp_id")
	private Employee employee;

	@ManyToOne
	@MapsId("roleId")
	@JoinColumn(name = "role_id")
	private RoleMaster role;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "role_active", nullable = false)
	private Boolean isActive;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}