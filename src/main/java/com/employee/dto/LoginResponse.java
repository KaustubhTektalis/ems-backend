package com.employee.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
	private String token;
	private String refreshToken;
	private String empId;
	private String companyEmail;
	private List<String> roles;

}
