package com.mmit.model.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mmit.model.entity.Orders;
import com.mmit.projection.DailyOrderSale;
import com.mmit.projection.MonthlyOrderSale;
import com.mmit.model.repos.*;

@Service
public class OrderService {

	@Autowired
	private OrderRepo repo;

	public Orders save(Orders new_order) {
		return repo.save(new_order);
	}

	public void deleteById(long id) throws Exception
	{
		Orders foundOrder = repo.findById(id).orElseThrow();
		
		boolean shouldDelete = foundOrder.receivedOrCancelled();
		if(shouldDelete)
			repo.deleteById(id);
		else
			throw new Exception("You cannot delete this");

	}
	
	public List<Orders> findAll() {
		return repo.findAll();
	}

	public Map<LocalDate, Long> findOrderSaleInEachDate() {
		List<Orders> orders = findAll();
		
		//List<Orders> latestOrders = repo.findLatestOrder();
		//System.out.println("Orders : " + latestOrders);
		
		//if(latestOrders.size() == 0)
			//return null;
		
		//LocalDate latestOrderDate = latestOrders.get(0).getCreated_at().toLocalDate();	

		//List<Orders> orders = new ArrayList<>();
		//if(orders.size() == 0)
			//return null;
		
		//orders.addAll(latestOrders);

		/*
		for(int x = 0; x < 4; x++)
		{
			List<Orders> orderBeforeLatestOrders = repo.findOrderBeforeLatestOrder(latestOrderDate);
			System.out.println("Order Before latest order : " + orderBeforeLatestOrders);
			
			if(orderBeforeLatestOrders.size() != 0)
			{
				orders.addAll(orderBeforeLatestOrders);
				latestOrderDate = orderBeforeLatestOrders.get(0).getCreated_at().toLocalDate();
			}
		}
		*/
		System.out.println("Order all : " + orders);
		
		Map<LocalDate, Long> sumOfOrderSalesByDate = orders.stream()
				.collect(
				Collectors.groupingBy(o -> o.getCreated_at().toLocalDate(), 
						Collectors.summingLong(o-> o.getAmount()))
						);
		
		TreeMap<LocalDate, Long> sortedSumOfOrderSalesByDate = new TreeMap<LocalDate, Long>(sumOfOrderSalesByDate);
		
		return sortedSumOfOrderSalesByDate;
	}

	public List<DailyOrderSale> findDailyOrderSalesInLastFiveDays()
	{
		return repo.findDailyOrderSalesInLastFiveDays();
	}
	
	public Orders findById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	public void updateOrderStatus(Orders order) {
		Orders foundOrder = repo.findById(order.getId()).orElse(null);
		if(foundOrder != null)
		{
			foundOrder.setStatus(order.getStatus());
			repo.save(foundOrder);
		}
	}

	public MonthlyOrderSale findLatestMonthOrderSales() {
		
		return repo.findLatestMonthOrderSales();
	}

	public long findPendingOrderCount() {
		return repo.findPendingOrderCount();
	}

	public List<Orders> findOrderByPendingAndDeliveredStatus(int id) {
		// TODO Auto-generated method stub
		return repo.findOrderByPendingAndDeliveredStatus(id);
	}

	public List<Orders> findByCustomerId(int id) {
		return repo.findByCustomerId(id);
	}
	

	
	
}
