package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatDto {

	private MessageType type;
	private String sender;
	private String userId;
	private String roomId;
	private String date;
	private String message;

	public ChatDto(Chat chat) {
		this.type = chat.getType();
		this.sender = chat.getSender();
		this.userId = chat.getEmail();
		this.roomId = chat.getRoom().getRoomId();
		this.message = chat.getMessage();
	}
}