package com.mmit.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mmit.model.entity.OrderItem;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.UserService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/orders")
	public String orderList(ModelMap map)
	{
		map.addAttribute("orderList", orderService.findAll());
		return "order";
	}
	

	@GetMapping("/shop/order/my-order")
	public String myOrder(ModelMap map, Principal principal)
	{
		System.out.println("Principal name " + principal.getName());
		User customer = userService.findByEmail(principal.getName());
		
		//map.addAttribute("orderList", orderService.findOrderByPendingAndDeliveredStatus());
		map.addAttribute("myOrderList", orderService.findOrderByPendingAndDeliveredStatus(customer.getId()));
		return "my-order";
	}
	
	@ResponseBody
	@GetMapping("/shop/order/order-item/{id}")
	public List<OrderItem> getOrderItem(@PathVariable("id") int id)
	{
		System.out.println("order id " + id);
		Orders orders = orderService.findById(id);
		return orders.getOrderItems();
	}
	
	@GetMapping("/order/detail/{id}")
	public String orderDetail(@PathVariable long id, ModelMap map)
	{
		map.addAttribute("order", orderService.findById(id));
		return "order-detail";
	}
	
	@PostMapping("/orders/update-status")
	public String orderUpdateStatus(Orders order, RedirectAttributes redirectAttr)
	{
		System.out.println("Order update " + order.getId());
		final long O_ID = order.getId();
		try
		{
			orderService.updateOrderStatus(order);
			System.out.println("Order status updated " + order);
			redirectAttr.addFlashAttribute("operation", "ok");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			redirectAttr.addFlashAttribute("operation", "fail");
		}


		return "redirect:/order/detail/" + O_ID;
	}
	
	@PostMapping("/order/delete")
	public String deleteOrder(long id, RedirectAttributes redirectAttr)
	{
		final long O_ID = id;
		System.out.println("ID " + id);
		try 
		{
			orderService.deleteById(id);
			redirectAttr.addFlashAttribute("operation", "ok");
			return "redirect:/orders";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			redirectAttr.addFlashAttribute("operation", "fail");
			return "redirect:/order/detail/" + O_ID;
		}

	}
	

}
