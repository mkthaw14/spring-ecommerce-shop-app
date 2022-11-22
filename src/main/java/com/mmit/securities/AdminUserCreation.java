package com.mmit.securities;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.service.UserService;

@Configuration
public class AdminUserCreation {

	@Autowired
	private UserService service;

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			long count = service.countUser();
			System.out.println("User Count : " + count);
			if (count == 0) {
				
				LocalDateTime t = LocalDateTime.now();
				
				User user1 = new User();
				user1.setEmail("admin@gmail.com");
				user1.setPassword("admin");
				user1.setRole(UserRole.admin);
				user1.setPhone("09123456");
				user1.setName("admin");
				user1.setAddress("Yangon");
				//user1.setExpired_at(t.plusMinutes(3));

				User user2 = new User();
				user2.setEmail("thaw@gmail.com");
				user2.setPassword("123");
				user2.setRole(UserRole.customer);
				user2.setPhone("09123456");
				user2.setName("thaw");
				user2.setAddress("Yangon");
				//user2.setExpired_at(t.plusMinutes(3));

				User user3 = new User();
				user3.setEmail("saw@gmail.com");
				user3.setPassword("123");
				user3.setRole(UserRole.customer);
				user3.setPhone("09123456");
				user3.setName("saw");
				user3.setAddress("Yangon");
				//user3.setExpired_at(t.plusMinutes(3));
				
				User user4 = new User();
				user4.setEmail("maung@gmail.com");
				user4.setPassword("123");
				user4.setRole(UserRole.customer);
				user4.setPhone("09123456");
				user4.setName("maung");
				user4.setAddress("Yangon");
				//user4.setExpired_at(t.plusMinutes(3));
				
				User user5 = new User();
				user5.setEmail("sayar@gmail.com");
				user5.setPassword("123");
				user5.setRole(UserRole.customer);
				user5.setPhone("09123456");
				user5.setName("sayar");
				user5.setAddress("Yangon");
				//user5.setExpired_at(t.plusMinutes(3));
				
				User user6 = new User();
				user6.setEmail("oil@gmail.com");
				user6.setPassword("123");
				user6.setRole(UserRole.customer);
				user6.setPhone("09123456");
				user6.setName("oil");
				user6.setAddress("Yangon");
				//user6.setExpired_at(t.plusMinutes(3));
				
				service.saveAndFlush(user1);

				service.saveAndFlush(user2);
				service.saveAndFlush(user3);
				service.saveAndFlush(user4);
				service.saveAndFlush(user5);
				service.saveAndFlush(user6);
			}
		};
	}


}
