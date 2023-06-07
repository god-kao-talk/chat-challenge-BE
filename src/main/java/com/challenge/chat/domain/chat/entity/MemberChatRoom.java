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

	private String roomId;
	private String memberEmail;

	public MemberChatRoom(String roomId, String email) {
		this.roomId = roomId;
		this.memberEmail = email;
	}
}
