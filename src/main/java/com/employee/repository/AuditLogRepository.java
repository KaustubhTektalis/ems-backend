package com.employee.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.employee.entity.AuditLog;
 
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{
 
}
