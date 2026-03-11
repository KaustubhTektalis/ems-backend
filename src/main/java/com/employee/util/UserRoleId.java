<<<<<<<< HEAD:src/main/java/com/employee/utils/UserRoleId.java
package com.employee.utils;

========
package com.employee.util;
 
>>>>>>>> origin/mounishk:src/main/java/com/employee/util/UserRoleId.java
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
<<<<<<<< HEAD:src/main/java/com/employee/utils/UserRoleId.java
import lombok.Data;
========
import lombok.EqualsAndHashCode;
import lombok.Getter;
>>>>>>>> origin/mounishk:src/main/java/com/employee/util/UserRoleId.java
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {
	private String empId;
	private Integer roleId;
}