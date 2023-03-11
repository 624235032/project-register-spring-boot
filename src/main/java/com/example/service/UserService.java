package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> getAll() {
		try {

			List<User> users = repository.findAll();

			if (users != null) {
				return users;
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<User> getUserByName(String firstName) {
		try {

			// check user firstName in database
			User userObj = getUserName(firstName);

			if (userObj != null) {
				return new ResponseEntity<>(userObj, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public List<User> getByStatusActive(String statusActive) {

		try {

			// Check statusActive (Y or N)
			if (!statusActive.equals("Y") && !statusActive.equals("N")) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			// check user statusActice in database
			List<User> userStatus = repository.findByStatusActive(statusActive);

			if (userStatus != null) {
				return userStatus;

			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<Map<String, String>> updateUserByEmail(String email, User user) {

		// check email user in database
		User userObj = getUserEmail(email);
		Map<String, String> response = new HashMap<>();

		if (userObj != null) {
			userObj.setFirstName(user.getFirstName());
			userObj.setLastName(user.getLastName());

		}
		repository.save(userObj);

		if (userObj != null) {
			response.put("firstName", userObj.getFirstName());
			response.put("lastName", userObj.getLastName());
			return ResponseEntity.ok(response);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<Map<String, String>> deleteUserById(String id) {

		try {

			// check Id in database
			User user = getUserId(id);
			Map<String, String> response = new HashMap<>();

			// check user statusActive/N
			if (user != null && !user.getStatusActive().equals("N")) {
				user.setStatusActive("N");
				repository.save(user);

				// return success
				response.put("message", "Delete Data Successfully ");
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
			// return error
			response.put("message", "User not found ");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Map<String, String>> deleteUserByfirstName(String firstName) {
		try {

			// check if firstName exist in database
			User user = getUserName(firstName);
			Map<String, String> response = new HashMap<>();

			// check user statusActive/N
			if (user != null && !user.getStatusActive().equals("N")) {
				user.setStatusActive("N");
				repository.save(user);

				// return success
				response.put("message", "Delete Data Successfully ");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			// return error
			response.put("message", "User not found ");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<Map<String, String>> deleteUserByEmail(String email) {
		try {

			// check if email exist in database
			User user = getUserEmail(email);
			Map<String, String> response = new HashMap<>();

			// check user statusActive/N
			if (user != null && !user.getStatusActive().equals("N")) {
				user.setStatusActive("N");
				repository.save(user);

				// return success
				response.put("message", "Delete Data Successfully ");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			// return error
			response.put("message", "User not found ");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

	public ResponseEntity<Map<String, String>> deleteAllUser() {

		// check user statusActice Y in database
		List<User> users = repository.findByStatusActive("Y");
		Map<String, String> response = new HashMap<>();

		// set statusActive/N save in database
		users.forEach(user -> user.setStatusActive("N"));
		repository.saveAll(users);
		response.put("message", "DeleteAll Data Successfully ");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// method findByfirstName
	private User getUserName(String firstName) {
		Optional<User> userObj = repository.findByfirstName(firstName);

		if (userObj.isPresent()) {
			return userObj.get();
		}
		return null;

	}

	// method findById
	private User getUserId(String id) {
		Optional<User> userObj = repository.findById(id);

		if (userObj.isPresent()) {
			return userObj.get();
		}
		return null;
	}

	// method findByEmail
	private User getUserEmail(String email) {

		Optional<User> user = Optional.of(repository.findByEmail(email));
		if (user.isPresent()) {
			return user.get();

		}
		return null;

	}

}
