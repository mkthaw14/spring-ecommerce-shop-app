package com.mmit.model.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;

public interface UserRepo extends JpaRepository<User, Integer> {

	@Query(name = "Select u from User u Where u.email = :email")
	User findUserByEmail(@Param("email") String email);

	@Query("Select u from User u Where u.role = :role")
	List<User> findAllByRole(@Param("role") UserRole role);

	@Query("Select u from User u Where u.role = :admin")
	User findByRole(@Param("admin") UserRole admin);
}
