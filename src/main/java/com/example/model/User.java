package com.example.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Role role;
	private String statusActive;
	private LocalDateTime updateDate;

	public User(String firstName, String lastName, String email, String password, Role role, LocalDateTime updateDate) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.statusActive = "Y";
		this.updateDate = updateDate;
	}

}
