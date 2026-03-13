package com.employee.services;



public interface AuditService {

    void log(String user, String action, String target);

}