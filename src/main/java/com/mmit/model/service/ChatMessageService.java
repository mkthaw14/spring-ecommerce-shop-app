package com.mmit.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.ChatMessage;
import com.mmit.model.repos.ChatMessageRepo;

@Service
public class ChatMessageService {

	@Autowired
	ChatMessageRepo repo;

	public List<ChatMessage> findByChatRoomId(String id) {
		return repo.findByChatRoomId(id);
	}

	public void save(ChatMessage msg) {
		repo.save(msg);
	}

	public ChatMessage findById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	@Transactional
	public void saveOrDeleteOldMessage(ChatMessage msg) {
		long count = repo.countByChatRoomId(msg.getChatRoom().getId());

		System.out.println("saved msg count : " + count);
		if(count > 2)
		{
		    //Delete old message
			var oldestMsg = repo.findFirstByChatRoomIdOrderByCreatedAtAsc(msg.getChatRoom().getId()).orElse(null);
			if(oldestMsg != null)
			{
				System.out.println("oldest : " + oldestMsg.getId() + " , " + oldestMsg.getCreatedAt());
				repo.delete(oldestMsg);
			}

		}
		
		repo.save(msg);
	}
	
	
}
