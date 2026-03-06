package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.entity.Password;
import com.employee.entity.Roles;
import com.employee.enums.RolesEnum;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Roles findByRole(RolesEnum role);

}