package com.mmit.controller.requestData;

public class ChatParticipantData {

	private String userName;
	private String chatId;

	
	public ChatParticipantData() {}

	public ChatParticipantData(String userName, String chatId) {
		this.userName = userName;
		this.chatId = chatId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	@Override
	public String toString() {
		return "ChatParticipantData [userName=" + userName + ", chatId=" + chatId + "]";
	}
	
	
	
}
