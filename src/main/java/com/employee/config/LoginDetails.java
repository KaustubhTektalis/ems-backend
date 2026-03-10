package com.employee.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.employee.dto.LoginResponse;
import com.employee.entity.Users;
import java.util.Collection;
import java.util.stream.Collectors;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
import com.employee.entity.Users;
import com.employee.repository.UserRoleRepository;
 
public class LoginDetails implements UserDetails {
 
    private final Users user;
    private final UserRoleRepository userRoleRepository;
 
    public LoginDetails(Users user,
                        UserRoleRepository userRoleRepository) {
 
        this.user = user;
        this.userRoleRepository = userRoleRepository;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
 
        return userRoleRepository
                .findByEmployeeEmpId(user.getEmpId())
                .stream()
                .map(role ->
                        new SimpleGrantedAuthority(
                                "ROLE_" + role.getRole().getRole().name()))
                .collect(Collectors.toList());
    }

 
    @Override
    public String getPassword() {
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
 
    @Override public boolean isAccountNonExpired(){ return true; }
    @Override public boolean isAccountNonLocked(){ return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
}