package com.mmit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.mmit.model.entity.ChatMessage;
import com.mmit.model.service.ChatMessageService;
import com.mmit.model.service.ChatRoomService;

@Controller
public class CustomerSupportController {

	@Autowired
	private ChatRoomService chatRoomService;

	
	@GetMapping("customer-support/customer-messages")
	public String goCustomerMessages(ModelMap map)
	{
		map.addAttribute("chatRooms", chatRoomService.findAll());
		return "customer-support-list";
	}
	
}
