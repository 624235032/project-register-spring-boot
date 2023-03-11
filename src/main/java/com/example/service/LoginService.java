package com.example.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.LoginRequest;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<Map<String, String>> login(LoginRequest loginRequest) {

		Map<String, String> response = new HashMap<>();
		User user = userRepository.findByEmail(loginRequest.getEmail());

		// check email password in database
		if (user == null || !matchPassword(loginRequest.getPassword(), user.getPassword())) {
			response.put("message", "Incorrect email or password");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// check users statusActive/N
		if (user.getStatusActive().equals("N")) {
			response.put("message", "Incorrect email or password");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		}
		// generate token
		String token = tokenService.generateToken(user);
		response.put("token", token);
		response.put("role", user.getRole().name());
		return ResponseEntity.ok(response);
	}

	private boolean matchPassword(String rawPassword, String encodePassword) {
		return passwordEncoder.matches(rawPassword, encodePassword);

	}

}
