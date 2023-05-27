package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

	private MessageType type;
	private String sender;
	private String userId;
	private String roomId;
	private String date;
	private String message;

	public ChatDto(MessageType type, String sender, String userId, String roomId, String message) {
		this.type = type;
		this.sender = sender;
		this.userId = userId;
		this.roomId = roomId;
		this.message = message;
	}

	public static ChatDto from(Chat chat) {
		return new ChatDto(
			chat.getType(),
			chat.getMember().getNickname(),
			chat.getMember().getEmail(),
			chat.getRoom().getRoomId(),
			chat.getMessage()
		);
	}
}