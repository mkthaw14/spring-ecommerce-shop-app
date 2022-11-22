package com.mmit.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.repos.*;



@Service
public class UserService {

	@Autowired
	private UserRepo repo;

	@Autowired
	private PasswordEncoder encoder;

	public long countUser() {
		return repo.count();
	}
	
	public void extendUserExpiredDate(String email)
	{
		User u = repo.findUserByEmail(email);
		LocalDateTime t = LocalDateTime.now();
		t = t.plusMinutes(15);
		
		if(u.getExpired_at() == null || u.getExpired_at().isBefore(LocalDateTime.now()))
		{
			u.setExpired_at(t);
			repo.save(u);
		}


		System.out.println(email + " , expired at : " + u.getExpired_at());
	}


	public void save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
	}
	
	public void saveAndFlush(User user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
		repo.saveAndFlush(user);
	}
	
	@Transactional
	public void registerUser(User user) throws Exception
	{
		User userEmail = repo.findUserByEmail(user.getEmail());
		if(userEmail != null)
			throw new Exception("Email exists");
		
		LocalDateTime date = LocalDateTime.now();
		user.setExpired_at(date.plusMinutes(3));
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
	}

	public User findByEmail(String email) {
		return repo.findUserByEmail(email);
	}

	public List<User> findAll() {
		return repo.findAll();
	}

	public List<User> findAllByRole(UserRole role) {
		return repo.findAllByRole(role);
	}

	public User findById(int id) {
		return repo.findById(id).orElse(null);
	}

	public User findByRole(UserRole admin) {
		return repo.findByRole(admin);
	}

	@Transactional
	public void saveAdmin(User user, String oldPassword, String newPassword) throws Exception {
		User u = repo.findByRole(user.getRole());

		System.out.println("old pass " + oldPassword + ", " + u.getPassword());
		System.out.println("matches " + encoder.matches(oldPassword, u.getPassword()));
		if(u.getRole() != UserRole.admin || !encoder.matches(oldPassword, u.getPassword()))
			throw new Exception("password doesn't match");
		
		
		user.setId(u.getId());
		user.setPassword(encoder.encode(newPassword));
		user.setCreated_at(u.getCreated_at());
		repo.save(user);
		
	}

	@Transactional
	public void deleteCustomerById(int id) throws Exception {
		
		User u = repo.findById(id).orElseThrow();
		if(u.getRole() == UserRole.admin)
			throw new Exception("Admin account cannot be deleted");
		
		repo.delete(u);
	}
}
