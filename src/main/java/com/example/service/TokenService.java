package com.example.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.model.User;

@Service
public class TokenService {

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtIssuer}")
	private String jwtIssuer;

	public String generateToken(User user) {
		
		// set the token 60 minutes
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 60);
		Date expiresAt = calendar.getTime();

		// create
		return JWT.create().withIssuer(jwtIssuer).withClaim("principal", user.getId()).withClaim("role", user.getRole().name())
				.withExpiresAt(expiresAt).sign(algorithm());
	}

	// verify
	public DecodedJWT verify(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm()).withIssuer(jwtIssuer).build();

			return verifier.verify(token);

		} catch (Exception e) {
			return null;
		}
	}

	private Algorithm algorithm() {
		return Algorithm.HMAC256(jwtSecret);
	}

}
