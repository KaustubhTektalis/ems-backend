package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.entity.Password;

public interface PasswordRepository extends JpaRepository<Password, String> {
}