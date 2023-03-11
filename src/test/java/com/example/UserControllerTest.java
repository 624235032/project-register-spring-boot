package com.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.controller.UserController;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {

	@Autowired
	private UserController userController;

	@MockBean
	private UserRepository userRepository;

	@Test
	@Order(1)
	public void testGetAll() {
		User user1 = new User("John", "Doe", "test123@gmail", "123456789", Role.ADMIN, null);
		User user2 = new User("sadas", "asdasd", "test345567@gmail", "321456", Role.NORMAL_USER, null);
		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);

		Mockito.when(userRepository.findAll()).thenReturn(userList);

		List<User> result = userController.getAll();

		assertEquals(userList, result);
	}

}
