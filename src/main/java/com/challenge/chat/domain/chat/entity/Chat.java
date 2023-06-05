package com.challenge.chat.domain.chat.entity;

import com.challenge.chat.domain.chat.dto.ChatDto;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Getter
@Document(collection = "chat")
@NoArgsConstructor
public class Chat {
	@Id
	private String id;

	private MessageType type;

	private String sender;

	private String userId;

	private String roomId;

	private String message;

	@CreatedDate
	private Instant createdAt;

	public Chat(ChatDto chatDto, MessageType messageType) {
		this.type = messageType;
		this.sender = chatDto.getSender();
		this.userId = chatDto.getUserId();
		this.roomId = chatDto.getRoomId();
		this.message = chatDto.getMessage();
//		this.createdAt = chatDto.getDate();
	}
}
