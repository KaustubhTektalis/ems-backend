package entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SetRoleId implements Serializable {
	private Integer empId;
	private Integer roleId;
}