package com.employee.config;

import java.security.Key;
import java.util.Date;

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
	private long accessExpiration;
	
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	
	public String generateAccessToken(String empId) {
		
		return Jwts.builder()
				
				.setSubject(empId)
				.claim("type", "access")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+accessExpiration))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	 public String generateRefreshToken(String username) {
 
	        return Jwts.builder()
	                .setSubject(username)
	                .claim("type", "refresh")
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
	                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
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