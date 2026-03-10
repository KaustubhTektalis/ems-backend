//package com.employee.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.employee.repository.SequenceRepository;
//
//
//@Component
//public class EmployeeIdGenerator {
//	@Autowired
//	private  SequenceRepository sequenceRepoistory;
//	/*public EmployeeIdGenerator(SequenceRepository sequenceRepoistory) {
//		this.sequenceRepoistory= sequenceRepoistory;
//	}*/
//	public String generateEmpId() {
//		Long seq=sequenceRepoistory.getNextEmployeeId();
//		return String.format("TT%04d", seq);
//	}
//}
