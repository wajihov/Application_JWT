package com.example.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(Constants.HEADER_STRING);
		System.out.println("Token : " + jwt);
		if (jwt == null || !jwt.startsWith(Constants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		Claims claims = Jwts.parser().setSigningKey(Constants.SECRET)
				.parseClaimsJws(jwt.replace(Constants.TOKEN_PREFIX, "")).getBody();
		String username = claims.getSubject();
		System.out.println("username : " + claims);
		// claims.get("role") role se trouve dans JWT le titre des autorites
		// ici le claims c'est la partie de Payload
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("role");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(r -> {
			System.out.println("Rolles : " + r);
			authorities.add(new SimpleGrantedAuthority(r.get("authority")));
		});

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

}
