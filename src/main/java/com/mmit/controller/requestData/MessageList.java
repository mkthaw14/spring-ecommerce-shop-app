package com.mmit.controller.requestData;

import java.util.ArrayList;
import java.util.List;

import com.mmit.model.entity.ChatMessage;
import com.mmit.model.entity.MessageData;

public class MessageList {

	private List<MessageData> messages;

	public List<MessageData> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageData> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "MessageList [messages=" + messages + "]";
	}

	
	
}
