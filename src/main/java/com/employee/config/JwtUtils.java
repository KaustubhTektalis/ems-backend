package com.employee.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
 
 
@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	@Value("${jwt.refreshExpiration}")
	private long refreshExpiration;
	@Value("${jwt.expiration}")
	private long expiration;
	
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	
	public String generateToken(String empId) {
		
		return Jwts.builder()
				
				.setSubject(empId)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
	            .getPayload();
	}
	
	public String extractUserName(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
 
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public boolean validateToken(String token, String userName) {
		String extractedUserName=extractUserName(token);
		return extractedUserName.equals(userName) && !isTokenExpired(token);
	}
	
}