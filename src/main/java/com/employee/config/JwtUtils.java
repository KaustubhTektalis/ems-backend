package com.employee.config;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	@Value("${ems.jwt.secret}")
	private String secret;

	@Value("${ems.jwt.expiration}")
	private long expiration;

	@Value("${ems.jwt.refreshExpiration}")
	private long refreshExpiration;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateAccessToken(String empId, List<String> roles) {

		return Jwts.builder().setSubject(empId).claim("roles", roles).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateRefreshToken(String empId) {

		return Jwts.builder().setSubject(empId).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {

		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token, String username) {

		String extracted = extractUsername(token);

		return extracted.equals(username);
	}
}