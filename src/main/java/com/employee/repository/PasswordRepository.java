package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.entity.Password;

public interface PasswordRepository extends JpaRepository<Password, String> {
}