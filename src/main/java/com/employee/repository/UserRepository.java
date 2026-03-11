package com.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

	Optional<Users> findByEmployeeCompanyEmail(String email);
}