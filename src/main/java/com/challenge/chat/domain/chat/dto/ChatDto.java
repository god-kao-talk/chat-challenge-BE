package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

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
		this.sender = chat.getMember().getNickname();
		this.userId = chat.getMember().getEmail();
		this.roomId = chat.getRoom().getRoomId();
		this.date = chat.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.message = chat.getMessage();
	}
}