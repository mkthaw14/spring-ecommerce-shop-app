package com.mmit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.service.UserService;

@Controller
public class CustomerController 
{

	@Autowired
	private UserService userService;
	
	@GetMapping("/customers")
	public String customerList(ModelMap map)
	{
		map.put("customerList", userService.findAllByRole(UserRole.customer));
		return "customer";
	}
	
	@GetMapping("/customer/detail/{id}")
	public String customerDetail(@PathVariable int id, ModelMap map)
	{
		map.put("customer", userService.findById(id));
		return "customer-detail";
	}
	
	@PostMapping("/customer/delete")
	public String customerDelete(int id, RedirectAttributes redirectAttr)
	{
		System.out.println("C ID " + id);
		try 
		{
			userService.deleteCustomerById(id);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			redirectAttr.addFlashAttribute("operation", "fail");
			return "redirect:/customers";
		}

		redirectAttr.addFlashAttribute("operation", "ok");
		return "redirect:/customers";
	}
}
