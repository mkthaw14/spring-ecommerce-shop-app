package com.mmit.model.entity;

public class MessageData {

	private long id;
	private String chatId;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MessageData [id=" + id + ", chatId=" + chatId + ", status=" + status + "]";
	}
	
	
	
}
