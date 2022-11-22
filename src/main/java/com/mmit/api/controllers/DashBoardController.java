package com.mmit.api.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmit.model.entity.OrderItem;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.Product;
import com.mmit.model.entity.User;
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;
import com.mmit.projection.DailyOrderSale;
import com.mmit.projection.MonthlyOrderSale;

@RestController
public class DashBoardController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	
	@GetMapping("/api/order/daily-sales")
	public ResponseEntity<Map<LocalDate, Long>> getDailyOrderSalesInLastFiveDays()
	{
		List<DailyOrderSale> dailyOrderSales = orderService.findDailyOrderSalesInLastFiveDays();
		Map<LocalDate, Long> sales = new TreeMap<LocalDate, Long>();
		System.out.println("daily Order Sales : " + dailyOrderSales.size());
		for(var s: dailyOrderSales)
		{
			System.out.println("daily Order Sales : " + s.getOrder_date() + " , " + s.getDaily_sales());
			sales.put(s.getOrder_date(), s.getDaily_sales());
		} 
		
		return new ResponseEntity<Map<LocalDate,Long>>(sales, HttpStatus.OK);
	}

	@GetMapping("/api/order/latest-monthly-sales")
	public ResponseEntity<Map<String, Long>> getOrderSalesForLatestMonth()
	{
		MonthlyOrderSale monthlySales = orderService.findLatestMonthOrderSales();
		Map<String, Long> sales = new HashMap<String, Long>();
		
		if(monthlySales == null)
		{	
			sales.put("", (long) 0);
			return new ResponseEntity<Map<String,Long>>(sales ,HttpStatus.OK);
		}
		
		System.out.println("Month Sales " + monthlySales.getMonth() + " , " + monthlySales.getMonthly_Sales());
		sales.put(monthlySales.getMonth(), monthlySales.getMonthly_Sales());
		return new ResponseEntity<Map<String,Long>>(sales ,HttpStatus.OK);
	}
	
	@GetMapping("api/order/pending-order-count")
	public ResponseEntity<Map<String, Long>> getPendingOrderCount()
	{
		long count = orderService.findPendingOrderCount();
		Map<String, Long> pendingCount = new HashMap<String, Long>();
		pendingCount.put("pendingOrderCount", count);
		return new ResponseEntity<Map<String,Long>>( pendingCount,HttpStatus.OK);
	}
	
	@GetMapping("api/product/number-of-products")
	public ResponseEntity<Map<String, Long>> getProductCount()
	{
		long count = productService.findCount();
		Map<String, Long> productCount = new HashMap();
		System.out.println("productCount : " + count);
		productCount.put("number of product", count);
		
		return new ResponseEntity<Map<String, Long>>( productCount,HttpStatus.OK);
	}
	
	@GetMapping("api/product/number-of-categories")
	public ResponseEntity<Map<String, Long>> getCategoryCount()
	{
		long count = categoryService.findCount();
		Map<String, Long> categoryCount = new HashMap();
		System.out.println("categoryCount : " + count);
		categoryCount.put("number of category", count);
		
		return new ResponseEntity<Map<String, Long>>( categoryCount,HttpStatus.OK);
	}
	


	
}
