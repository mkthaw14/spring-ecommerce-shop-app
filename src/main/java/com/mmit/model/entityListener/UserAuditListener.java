package com.mmit.model.entityListener;

import java.time.LocalDateTime;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.mmit.model.entity.ChatRoom;
import com.mmit.model.entity.CustomerSupportID;
import com.mmit.model.entity.User;
import com.mmit.model.repos.ChatRoomRepo;
import com.mmit.model.service.ChatRoomService;
import com.mmit.model.service.UserService;

@Component
public class UserAuditListener {
	
	private static ChatRoomService chatRoomService;

	
	@Autowired
	public void init(ChatRoomService service)
	{
		UserAuditListener.chatRoomService = service;

	}
	
	
	@PrePersist
	public void beforePersist(User user)
	{
		user.setCreated_at(LocalDateTime.now());
		user.setUpdated_at(LocalDateTime.now());
		user.setExpired_at(LocalDateTime.now().plusMinutes(15));
		System.out.println("before save : " + user.getName());
	}
	
	@PreUpdate
	public void beforeUpdate(User user)
	{
		user.setUpdated_at(LocalDateTime.now());
		user.setExpired_at(LocalDateTime.now().plusMinutes(15));
		System.out.println("after update : " + user.getName());
	}
	
	@PostPersist
	public void afterPersist(User user)
	{
		System.out.println("after save : " + user.getName() + ", " + user.getId());


		//ChatRoom room = chatRoomService.findChatRoomByUserNames(user.getEmail(), CustomerSupportID.getCustomerSupportChannelId());
		ChatRoom room = new ChatRoom();
		room.setUser1(user.getEmail());
		room.setUser2(CustomerSupportID.getCustomerSupportChannelId());	
		chatRoomService.save(room);
	}
	
	@PostUpdate
	public void afterUpdate(User user)
	{
		System.out.println("after update : " + user.getName());
	}
	
	@PreRemove
	public void beforeRemove(User user) throws Exception
	{
		System.out.println("After remove : " + user.getEmail());
		ChatRoom room = chatRoomService.findChatRoomByUser1AndUser2(user.getEmail(), CustomerSupportID.getCustomerSupportChannelId());
		System.out.println("room : " + room.getId());
		chatRoomService.delete(room);
	}
	
}
