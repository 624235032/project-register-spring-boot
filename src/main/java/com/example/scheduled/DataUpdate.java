package com.example.scheduled;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.repository.UserRepository;

@Component
public class DataUpdate {

	private final Logger logger = LoggerFactory.getLogger(DataUpdate.class);

	@Autowired
	private UserRepository repository;

	@Scheduled(cron = "0 0 17 * * *", zone = "Asia/Bangkok")
	public void updateData() {
		
		// get all users to update
		List<User> userUpdate = repository.findAll();

		// update users
		for (User user : userUpdate) {

			ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Bangkok"));
			user.setUpdateDate(now.toLocalDateTime());

			logger.info("Data update completed at {}", ZonedDateTime.now());

			// save updated user
			repository.save(user);

		}
	}

}
