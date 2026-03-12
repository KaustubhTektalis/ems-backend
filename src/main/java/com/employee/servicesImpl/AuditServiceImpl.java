package com.employee.servicesImpl;

import org.springframework.stereotype.Service;

import com.employee.entity.AuditLog;
import com.employee.repository.AuditLogRepository;
import com.employee.services.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void log(String user, String action, String target) {

        AuditLog log = AuditLog.builder()
                .user(user)
                .action(action)
                .target(target)
                .build();

        auditLogRepository.save(log);
    }
}
