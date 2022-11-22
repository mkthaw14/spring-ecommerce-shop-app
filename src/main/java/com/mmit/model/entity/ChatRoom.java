package com.mmit.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmit.uidGenerator.MyRandomIDGenerator;

@Entity
@Table(
		uniqueConstraints = { @UniqueConstraint(columnNames = {"user1", "user2"})}
		)
public class ChatRoom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = MyRandomIDGenerator.generatorName)
	@GenericGenerator(name = MyRandomIDGenerator.generatorName, strategy = "com.mmit.uidGenerator.MyRandomIDGenerator")
	private String id;
	
	private String user1;
	private String user2;
	
	@JsonIgnore
	@OneToMany(mappedBy = "chatRoom",  orphanRemoval = true)
	private List<ChatMessage> chatMessages;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	
	@JsonIgnore
	public int getUnreadChatMessagesCountSendByCustomers()
	{
		int count = 0;
		if(this.chatMessages != null && this.chatMessages.size() > 0)
		{
			for(int x = 0; x < this.chatMessages.size(); x++)
			{
				if(chatMessages.get(x).getMessageStatus() == MessageStatus.unread && ! chatMessages.get(x).getSenderName().equals("Site Owner"))
				{
					count++;	
				}
			}
		}
		
		return count;
	}
	
	@JsonIgnore
	public int getUnreadChatMessageCountSendBySiteOwner()
	{
		int count = 0;
		if(this.chatMessages != null && this.chatMessages.size() > 0)
		{
			for(int x = 0; x < this.chatMessages.size(); x++)
			{
				if(chatMessages.get(x).getMessageStatus() == MessageStatus.unread && chatMessages.get(x).getSenderName().equals("Site Owner")) 
				{
					count++;
				}
			}
		}
		
		return count;
	}
	
	
	public List<ChatMessage> getChatMessages() {
		return chatMessages;
	}
	public void setChatMessages(List<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}
	
	@JsonIgnore
	public String getFirstLetterOfName()
	{
		if(this.user1 == null)
			return "";
		
		String firstLetterName = this.user1.substring(0, 1);
		return firstLetterName.toUpperCase();
	}
}
