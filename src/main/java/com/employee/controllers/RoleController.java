package com.employee.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.services.RoleService;

@RestController
@RequestMapping("/admin/roles")
public class RoleController {
	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}


	@PostMapping("/assign")
	public void assignRole(@RequestParam String empId, @RequestParam String role) {
		roleService.assignRole(empId, role);

	}

	@PostMapping("/remove")
	public void removeRole(@RequestParam String empId, @RequestParam String role) {
		roleService.removeRole(empId, role);

	}

}
