package com.mmit.model.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	@Query(value = "Select Count(*) From products", nativeQuery = true)
	long getCount();

	@Query(value = "Select p From Product p Where p.category.id = :id")
	List<Product> findByCategory(@Param("id") int id);
}
