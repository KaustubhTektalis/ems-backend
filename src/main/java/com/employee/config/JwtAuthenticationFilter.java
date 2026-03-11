package com.employee.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final LoginDetailsService loginDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils,
                                   LoginDetailsService loginDetailsService) {

        this.jwtUtils = jwtUtils;
        this.loginDetailsService = loginDetailsService;
    }
   @Override
   protected boolean shouldNotFilter(HttpServletRequest request) {
	   return request.getServletPath().startsWith("/auth");
   }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {

            token = header.substring(7);

            username = jwtUtils.extractUsername(token);
        }

        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails =
                    loginDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(token,
                    userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}