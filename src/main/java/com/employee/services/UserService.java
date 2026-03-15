package com.employee.services;

public interface UserService {
	
	public void updatePassword(String empId, String updatedPassword);
	public void resetPassword(String empId);

}
