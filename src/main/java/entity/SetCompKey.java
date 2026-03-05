package entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SetCompKey implements Serializable {
	private String empId;
	private Integer roleId;
}