package com.employee.config;

import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.employee.entity.Users;

public class LoginDetails implements UserDetails {
	private final Users user;
	private final List<SimpleGrantedAuthority> authorities;
	public LoginDetails(Users user,List<SimpleGrantedAuthority> authorities) {
		this.user=user;
		this.authorities=authorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}
	@Override
	public String  getPassword() {
	
		return user.getPassword();
	}
	@Override
	public String getUsername() {
		
		return user.getEmpId();
	}
	@Override
	public boolean isEnabled() {
		return user.isUserActive();
	}
	@Override
	public  boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public  boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	
	}

}
