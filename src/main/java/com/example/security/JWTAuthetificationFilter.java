package com.example.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.entities.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthetificationFilter extends UsernamePasswordAuthenticationFilter {

	/*
	 * @Autowired private AuthenticationManager authenticationManager;
	 */
	private AuthenticationManager authenticationManager;

	public JWTAuthetificationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AppUser appUser = null;
		try {
			appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		} catch (Exception e) {
			throw new RuntimeException("Dans filter JWTAuthetification : " + e);
		}
		System.out.println("***********************\n " + appUser.getUsername());
		System.out.println("\n " + appUser.getPassword());
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User springUser = (User) authResult.getPrincipal();
		String jwt = Jwts.builder().setSubject(springUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, Constants.SECRET).claim("role", springUser.getAuthorities())
				.compact();
		response.addHeader(Constants.HEADER_STRING, Constants.TOKEN_PREFIX + jwt);
	}
}
