package com.employee.exceptions;

public class InactiveEmployeeException extends RuntimeException {

	public InactiveEmployeeException(String message) {
		super(message);
	}
}