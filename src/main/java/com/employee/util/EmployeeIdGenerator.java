package com.employee.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employee.repository.EmployeeRepository;


@Component
public class EmployeeIdGenerator {
	@Autowired
	private EmployeeRepository employeeRepository ;
	/*public EmployeeIdGenerator(SequenceRepository sequenceRepoistory) {
		this.sequenceRepoistory= sequenceRepoistory;
	}*/
	public String generateEmpId() {
		Long seq=employeeRepository.getNextEmployeeId();
		return String.format("TT%04d", seq);
	}
}
