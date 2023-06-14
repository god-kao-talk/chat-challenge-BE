package com.challenge.chat.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

import javax.persistence.Id;

@Document(collection = "chatroom")
@Getter
@NoArgsConstructor
public class ChatRoom {
	@Id
	private String id;

	private String roomId;
	private String roomName;

	@CreatedDate
	private Instant createdAt;

	private ChatRoom(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public static ChatRoom of(String roomId, String roomName) {
		return new ChatRoom(roomId, roomName);
	}
}