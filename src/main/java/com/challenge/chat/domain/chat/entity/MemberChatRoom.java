package com.challenge.chat.domain.chat.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "memberchatroom")
@Getter
@NoArgsConstructor
public class MemberChatRoom {
	@Id
	private String id;

	private String roomCode;
	private String email;

	public MemberChatRoom(String roomCode, String email) {
		this.roomCode = roomCode;
		this.email = email;
	}
}
