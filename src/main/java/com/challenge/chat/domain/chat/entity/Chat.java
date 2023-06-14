package com.challenge.chat.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

import javax.persistence.Id;

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

	private Chat(MessageType type, String sender, String userId, String roomId, String message) {
		this.type = type;
		this.sender = sender;
		this.userId = userId;
		this.roomId = roomId;
		this.message = message;
	}

	public static Chat of(MessageType type, String sender, String userId, String roomId, String message) {
		return new Chat(type, sender, userId, roomId, message);
	}
}
