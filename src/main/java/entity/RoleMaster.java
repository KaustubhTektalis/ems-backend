package entity;

import enums.RolesEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "set_role_id")
@Data
public class RoleMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, unique = true)
	private RolesEnum role;
}