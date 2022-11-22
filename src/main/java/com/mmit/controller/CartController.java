package com.mmit.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmit.controller.requestData.OrderItemData;
import com.mmit.controller.requestData.OrderReceiverData;
import com.mmit.controller.requestData.OrderRequestData;
import com.mmit.model.entity.OrderItem;
import com.mmit.model.entity.OrderStatus;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;

@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/cart/detail")
	public String home()
	{
		return "cart";
	}
	
	@GetMapping("/cart/checkout")
	public String checkoutPage(ModelMap map, Principal principal)
	{
		System.out.println("Principal " + principal);
		User loginUser = userService.findByEmail(principal.getName());
		map.put("name", loginUser.getName());
		map.put("phone", loginUser.getPhone());
		map.put("email", loginUser.getEmail());
		map.put("address", loginUser.getAddress());
		System.out.println("checkout page");
		return "checkout";
	}
	
	@PostMapping("/cart/place-order") // Annotating the return type of func with ResponseBody means it will not return view pages
	public @ResponseBody String placeOrder(@RequestBody OrderRequestData obj, Principal principal)
	{
		try
		{
			OrderReceiverData receiverData = obj.getReceiver();
			List<OrderItemData> orderItemDataList = obj.getItemDataList();
			
			System.out.println("place order : " + obj);
			
			if(receiverData == null)
				throw new Exception("no receiver data");
			if(orderItemDataList == null || orderItemDataList.size() == 0)
				throw new Exception("no order item dataList");
				
			User customer = userService.findByEmail(principal.getName()); // get email from current login user
			if(customer == null)
				throw new Exception("customer email not found");

			Orders new_order = new Orders();
			new_order.setOrderReceiverData(receiverData, customer);
			new_order.setOrderItemList(orderItemDataList, productService);
			
			Orders savedOrder = orderService.save(new_order);
			System.out.println("order successfully saved");
			return savedOrder.getId() + "";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("save operation failed");
			return "save operation failed";
		}

	}
}
