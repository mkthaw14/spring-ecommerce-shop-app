package com.mmit.model.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mmit.model.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	@Query(value = "Select Count(*) From categories", nativeQuery = true)
	long getCount();

	
}
