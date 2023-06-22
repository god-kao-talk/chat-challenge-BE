package com.challenge.chat.domain.chat.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "chatroom")
@Getter
@NoArgsConstructor
public class ChatRoom {
	@Id
	private String id;

	private String roomCode;
	private String roomName;

	@CreatedDate
	private Instant createdAt;

	private ChatRoom(String roomCode, String roomName) {
		this.roomCode = roomCode;
		this.roomName = roomName;
	}

	public static ChatRoom of(String roomCode, String roomName) {
		return new ChatRoom(roomCode, roomName);
	}
}