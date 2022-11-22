package com.mmit.model.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> 
{
	@Query("Select o From OrderItem o Where o.product.id = :pID")
	List<OrderItem> findByProductID(@Param("pID") int pID); 
}
