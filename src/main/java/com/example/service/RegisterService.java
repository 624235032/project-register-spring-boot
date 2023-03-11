package com.example.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.RoleConstaants;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class RegisterService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailSendService emailSendService;

	@Value("${spring.mail.username}")
	private String from;

	public ResponseEntity<Map<String, String>> registerUser(User user, String role) {

		Map<String, String> response = new HashMap<>();
		User existUser = userRepository.findByEmail(user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// check email in database
		if (existUser != null && !existUser.getStatusActive().equals("N")) {
			response.put("message", "Email already in use");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		}

		// save user and send mail notification
		User saveUser = userRepository.save(user);
		if (saveUser != null) {
			response.put("firstName", saveUser.getFirstName());
			response.put("email", saveUser.getEmail());
			sendEmail(saveUser);
			return ResponseEntity.ok(response);

		}

		// set role
		if (role != null && role.equals("ADMIN")) {
			if (role.equals(RoleConstaants.ADMIN)) {
				user.setRole(Role.ADMIN);
			} else {
				user.setRole(Role.NORMAL_USER);
			}
		}

		return null;

	}

	// method sendEmail sign up successfully
	private void sendEmail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Sign up Successfully");
		mailMessage.setFrom(from);
		mailMessage.setText(
				"เรียนคุณ " + user.getFirstName() + " " + user.getLastName() + " ยินดีด้วยคุณสมัครสมาชิกสำเร็จ !!!");
		emailSendService.sendEmail(mailMessage);
	}

}
