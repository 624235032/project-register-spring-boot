package com.example.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.model.Role;
import com.example.service.TokenService;

public class TokenFilter extends GenericFilterBean {

	private final TokenService tokenService;

	public TokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String authorization = request.getHeader("Authorization");

		// check authorization
		if (ObjectUtils.isEmpty(authorization)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		// check authorization bearer token
		if (!authorization.startsWith("Bearer ")) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		// verify token
		String token = authorization.substring(7);
		DecodedJWT decoded = tokenService.verify(token);

		if (decoded == null) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		// extract user and role from token
		String principal = decoded.getClaim("principal").asString();
		String role = decoded.getClaim("role").asString();

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
				"(protected)", authorities);

		// set authentication security context
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);

		// continue with filterChain
		filterChain.doFilter(servletRequest, servletResponse);
	}

}
