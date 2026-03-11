package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.entity.Roles;
import com.employee.enums.RolesEnum;
@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

    Roles findByRole(RolesEnum role);

}