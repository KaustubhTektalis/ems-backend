package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.entity.Password;
import com.ems.entity.Roles;
import com.ems.enums.RolesEnum;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Roles findByRole(RolesEnum role);

}