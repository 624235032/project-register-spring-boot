package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.LoginRequest;
import com.example.model.User;
import com.example.service.LoginService;
import com.example.service.RegisterService;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	@Tag(name = "Login-Register")
	public ResponseEntity<Map<String, String>> register(@RequestBody User user, String role) {
		return registerService.registerUser(user, role);
	}

	@PostMapping("/login")
	@Tag(name = "Login-Register")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
		return loginService.login(loginRequest);
	}

	@GetMapping("/getAll")
	@Tag(name = "Get-User")
	public List<User> getAll() {
		return userService.getAll();

	}

	@GetMapping("/getbyfirstName/{firstName}")
	@Tag(name = "Get-User")
	public ResponseEntity<User> getUserByName(@PathVariable("firstName") String firstName) {
		return userService.getUserByName(firstName);

	}

	@GetMapping("/getbystatusActive/{statusActive}")
	@Tag(name = "Get-User")
	public List<User> getByStatusActive(@PathVariable String statusActive) {
		return userService.getByStatusActive(statusActive);

	}

	@PutMapping("/update/{email}")
	@Tag(name = "Edit-User-ByEmail")
	public ResponseEntity<Map<String, String>> updateUserByEmail(@PathVariable("email") String email,
			@RequestBody User user) {
		return userService.updateUserByEmail(email, user);

	}

	@DeleteMapping("/deletebyId/{id}")
	@Tag(name = "Delete-User")
	public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable("id") String id) {
		return userService.deleteUserById(id);
	}

	@DeleteMapping("/deletebyfirstName/{firstName}")
	@Tag(name = "Delete-User")
	public ResponseEntity<Map<String, String>> deleteUserByfirstName(@PathVariable("firstName") String firstName) {
		return userService.deleteUserByfirstName(firstName);
	}

	@DeleteMapping("/deletebyEmail/{email}")
	@Tag(name = "Delete-User")
	public ResponseEntity<Map<String, String>> deleteUserByEmail(@PathVariable("email") String email) {
		return userService.deleteUserByEmail(email);
	}

	@DeleteMapping("/deleteAll")
	@Tag(name = "Delete-User")
	public ResponseEntity<Map<String, String>> deleteAllUser() {
		return userService.deleteAllUser();
	}
	


}
