package com.employee.controllers;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.services.UserService;
//import com.employee.servicesImpl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RequestMapping
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PatchMapping("update/password/{empId")
	public void updatePassword(@PathVariable String empId, @RequestBody String updatedPassword) {
		userService.updatePassword(empId, updatedPassword);
	}

	@PatchMapping("reset/password/{empId}")
	public void resetPassword(@PathVariable String empId) {
		userService.resetPassword(empId);
	}

}