package com.mmit.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mmit.model.entity.CustomerSupportID;
import com.mmit.model.entity.User;
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.ChatRoomService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;


@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@GetMapping("/")
	public String home()
	{
		return "redirect:/shop";
	}
	
	@GetMapping("/admin")
	public String admin()
	{
		return "redirect:/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String adminDashBoard()
	{
		return "dashboard";
	}
	
	@GetMapping("/shop")
	public String goShop(ModelMap map)
	{
		map.put("productList", productService.findAll());
		map.put("categoryList", categoryService.findAll());
		return "index";
	}
	
	@GetMapping("/shop/about")
	public String goAbout()
	{
		return "about";
	}
	
	@GetMapping("/shop/contact")
	public String goContact(ModelMap map, Principal principal)
	{
		map.put("chatRoom", chatRoomService.findChatRoomByUser1AndUser2(principal.getName(), CustomerSupportID.getCustomerSupportChannelId()));
		return "contact";
	}
	
	@GetMapping("/shop/category/{id}")
	public String showByCategory(@PathVariable int id, ModelMap map)
	{
		map.put("productList", productService.findByCategory(id));
		map.put("categoryList", categoryService.findAll());
		return "index";
	}
	
	@GetMapping("/shop/login")
	public String loginPage()
	{
		return "login";
	}

	
	@GetMapping("/shop/product/{id}")
	public String singleProduct(@PathVariable("id") long id, ModelMap map)
	{
		var product = productService.findById(id);
		System.out.println("photo " + product.getPhotoPath());
		map.put("currentProduct", product);
		return "product-detail";
	}
	

	
}
