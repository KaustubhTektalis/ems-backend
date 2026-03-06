package com.ems.utils;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class EmployeeIdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {

		Long sequenceValue = ((Number) session.createNativeQuery("SELECT nextval('employee_seq')").getSingleResult())
				.longValue();

		return "TT" + String.format("%04d", sequenceValue);
	}
}
