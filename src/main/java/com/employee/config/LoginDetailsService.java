package com.employee.config;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employee.entity.Users;
import com.employee.repository.UserRepository;
import com.employee.repository.UserRoleRepository;

@Service
public class LoginDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public LoginDetailsService(UserRepository userRepository,
                               UserRoleRepository userRoleRepository) {

        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Users user;

        if (username.contains("@")) {

            user = userRepository
                    .findByEmployeeCompanyEmail(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));
        }
        else {

            user = userRepository
                    .findById(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));
        }

        return new LoginDetails(user, userRoleRepository);
    }
}