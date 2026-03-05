package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Password;

public interface PasswordRepo extends JpaRepository<Password,String> {

}
