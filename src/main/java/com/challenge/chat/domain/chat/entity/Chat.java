package com.challenge.chat.domain.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
// @Document(collection = "chat")
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
	// @Id
	// private String id;

	private MessageType type;

	private String nickname;

	private String email;

	private String roomCode;

	private String message;

	// @CreatedDate
	private long createdAt;

	private Chat(MessageType type, String nickname, String email, String roomCode, String message) {
		this.type = type;
		this.nickname = nickname;
		this.email = email;
		this.roomCode = roomCode;
		this.message = message;
	}

	public static Chat of(MessageType type, String nickname, String email, String roomCode, String message) {
		return new Chat(type, nickname, email, roomCode, message);
	}

	public void setCreatedAt(Instant time) {
		this.createdAt = time.toEpochMilli();
	}
}
