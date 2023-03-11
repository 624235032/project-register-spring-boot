package com.example.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Employee {
	
	@Id
	private String id;
	private String name;
	private String lastName;
	



}
