package com.mmit.model.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.ChatMessage;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {
	@Query(value = "Select * From chat_message Where chat_room_id = :chatID", nativeQuery = true)
	List<ChatMessage> findByChatRoomId(@Param("chatID") String chatID);


	Optional<ChatMessage> findFirstByChatRoomIdOrderByCreatedAtAsc(@Param("chatID") String chatID);
	
	@Query(value = "Select Count(*) From chat_message Where chat_room_id = :chatID", nativeQuery = true)
	long countByChatRoomId(@Param("chatID") String chatID);
}
