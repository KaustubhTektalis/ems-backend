package com.employee.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.services.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;

//	public RoleController(RoleService roleService) {
//		this.roleService = roleService;
//	}

	@PostMapping("/assign/{empId}")
	public void assignRole(@PathVariable String empId, @RequestParam String role) {
		roleService.assignRole(empId, role);

	}

	@PostMapping("/remove/{empId}")
	public void removeRole(@PathVariable String empId, @RequestParam String role) {
		roleService.removeRole(empId, role);

	}

}
