package com.mmit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public String userList(ModelMap map)
	{
		map.addAttribute("userList", userService.findAll());
		return "user";
	}
	
	@GetMapping("/user/admin")
	public String adminUser(ModelMap map)
	{
		map.put("adminUser", userService.findByRole(UserRole.admin));
		return "admin-detail";
	}
	
	@GetMapping("/user/admin/edit")
	public String adminEdit(ModelMap map)
	{
		map.put("adminUser", userService.findByRole(UserRole.admin));
		return "admin-edit";
	}
	
	@GetMapping("/shop/user/register")
	public String registerPage(ModelMap map)
	{
		map.put("user", new User());
		return "register";
	}
	
	@PostMapping("/shop/user/add")
	public String userAdd(@Valid @ModelAttribute User user, BindingResult result)
	{
		System.out.println("User " + user);
		user.setRole(UserRole.customer);
		try 
		{
			userService.registerUser(user);
			return "redirect:/shop/login";
		} 
		catch (Exception e) 
		{
			result.addError(new ObjectError("global", e.getMessage()));
			return "register";
		}

	}
	
	@PostMapping("/user/save")
	public String userSave(User user, @RequestParam("old-password") String oldPassword, @RequestParam("new-password") String newPassword, RedirectAttributes redirectAttr)
	{
		System.out.println("old pass "  + oldPassword );
		System.out.println("new pass " + newPassword);
		System.out.println("User : " + user);
		try {
			userService.saveAdmin(user, oldPassword, newPassword);
			redirectAttr.addFlashAttribute("operation", "ok");
			return "redirect:/user/admin";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttr.addFlashAttribute("operation", "fail");
			return "redirect:/user/admin/edit";
		}

	}
	
	@PostMapping("/user/extend-expired_date")
	public String extendAccountExpiredDate(@RequestAttribute String userName) throws Exception
	{		
		try 
		{	
			userService.extendUserExpiredDate(userName);
			return "redirect:/";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "";
		}

	}
}
