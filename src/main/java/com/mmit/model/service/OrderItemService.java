package com.mmit.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mmit.model.entity.OrderItem;
import com.mmit.model.repos.*;

@Service
public class OrderItemService {

	private OrderItemRepo repo;
	
	public List<OrderItem> findByProductID(int id)
	{
		return repo.findByProductID(id);
	}
}
