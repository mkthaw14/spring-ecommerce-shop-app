package com.mmit.api.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmit.controller.requestData.ChatParticipantData;
import com.mmit.controller.requestData.MessageList;
import com.mmit.model.entity.ChatMessage;
import com.mmit.model.entity.ChatRoom;
import com.mmit.model.entity.CustomerSupportID;
import com.mmit.model.entity.MessageData;
import com.mmit.model.entity.MessageStatus;
import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.service.ChatMessageService;
import com.mmit.model.service.ChatRoomService;
import com.mmit.model.service.UserService;

@RestController
public class ChatController 
{
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	

	
	@PostMapping("/chat/get-customer_support_operator_name")
	public String getCustomerSupportOperatorName()
	{
		return CustomerSupportID.getSiteOwnerName();
	}

	@PostMapping("/update-msg")
	public String updateMessageStatus(@RequestBody MessageList messages)
	{
		if(messages != null && messages.getMessages() != null && messages.getMessages().size() > 0)
		{
			for(MessageData msgData : messages.getMessages())
			{
				ChatMessage chatMsg = chatMessageService.findById(msgData.getId());
				if(chatMsg == null)
					break;
				
				chatMsg.setMessageStatus(MessageStatus.read);
				chatMessageService.save(chatMsg);
			}
		}
		System.out.println(messages);
		return "Ok";
	}
	
	
	@PostMapping("/chat/get-chatId")
	public ChatParticipantData getChatId(Principal principal)
	{
		//System.out.println(principal.getName());
		
		//ChatRoom room = chatRoomService.findChatRoomByUserNames(principal.getName(), CustomerSupportID.getCustomerSupportChannelId());
		ChatRoom room = chatRoomService.findChatRoomByUser1AndUser2(principal.getName(), CustomerSupportID.getCustomerSupportChannelId());

		/*if(room == null)
		{
			room = new ChatRoom();
			room.setUser1(principal.getName());
			room.setUser2(CustomerSupportID.getCustomerSupportChannelId());	
			chatRoomService.save(room);
		} */
		
		return new ChatParticipantData(principal.getName(), room.getId());
	}
	
	@PostMapping("/chat/load-offline-msg")
	public List<ChatMessage> loadOfflineMessages(String chatId)
	{
		List<ChatMessage> messages = chatMessageService.findByChatRoomId(chatId);
		
		System.out.println("loaded msg : " + messages);
		return messages;
	}
	
	@MessageMapping("/private-msg")
	public void sendMessageToSiteAdmin(@Payload ChatMessage msg, Principal principal) throws Exception
	{
		System.out.println("Message : " + msg);
		msg.setSenderName(principal.getName());
		msg.setMessageStatus(MessageStatus.unread);
		
		if(msg.getContent().isEmpty() || msg.getContent().isBlank())
			throw new Exception("msg content is empty");
		
		chatMessageService.saveOrDeleteOldMessage(msg);
		simpMessagingTemplate.convertAndSendToUser(msg.getChatRoom().getId(), "customer-private-chat-room", msg);
	}
	
	@MessageMapping("/private-msg-to-customer")
	public void sendMessageToCustomer(@Payload ChatMessage message, Principal principal) throws Exception
	{
		message.setSenderName(CustomerSupportID.getSiteOwnerName());
		message.setMessageStatus(MessageStatus.unread);
		
		if(message.getContent().isEmpty() || message.getContent().isBlank())
			throw new Exception("msg content is empty");
		
		chatMessageService.saveOrDeleteOldMessage(message);
		simpMessagingTemplate.convertAndSendToUser(message.getChatRoom().getId(), "customer-private-chat-room", message);
	}
	
}
