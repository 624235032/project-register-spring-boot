package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByfirstName(String firstName);

	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

	void deleteByfirstName(String firstName);

	List<User> findByStatusActive(String statusActive);




}
