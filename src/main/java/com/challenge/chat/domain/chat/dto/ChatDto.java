package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.ChatES;
import com.challenge.chat.domain.chat.entity.MessageType;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

	private MessageType type;
	private String nickname;
	private String email;
	private String roomCode;
	private String message;
	private Instant createdAt;
	private String imageUrl;

	public static ChatDto from(Chat chat) {

		Instant instant;
		if (chat.getCreatedAt() == null) {
			instant = null;
		} else {
			double timestampValue = Double.parseDouble(chat.getCreatedAt());
			long epochSeconds = (long) timestampValue;
			instant = Instant.ofEpochSecond(
				epochSeconds,
				(int) ((timestampValue - epochSeconds) * 1_000_000_000));
		}

		return new ChatDto(
			chat.getType(),
			chat.getNickname(),
			chat.getEmail(),
			chat.getRoomCode(),
			chat.getMessage(),
			instant,
			chat.getImageUrl()
		);
	}

	public static Chat toEntity(ChatDto chatDto) {
		return Chat.of(
			chatDto.getType(),
			chatDto.getNickname(),
			chatDto.getEmail(),
			chatDto.getRoomCode(),
			chatDto.getMessage()
		);
	}

	public static ChatDto from(ChatES chat) {
		return new ChatDto(
			chat.getType(),
			chat.getNickname(),
			chat.getEmail(),
			chat.getRoomCode(),
			chat.getMessage(),
			Instant.ofEpochMilli(chat.getCreatedAt()),
			chat.getImageUrl()
		);
	}
}