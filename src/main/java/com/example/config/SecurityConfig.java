package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.service.TokenService;
import com.example.token.TokenFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenService tokenService;

	public SecurityConfig(TokenService tokenService) {
		this.tokenService = tokenService;
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.cors().and().csrf().disable()
	        .authorizeRequests()
	        .antMatchers("/api/users/login").permitAll()
	        .antMatchers("/api/users/register").permitAll()
	        .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    http.addFilterBefore(new TokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
