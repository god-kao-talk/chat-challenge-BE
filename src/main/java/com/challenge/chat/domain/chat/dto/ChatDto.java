package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

	private MessageType type;
	private String nickname;
	private String email;
	private String roomId;
	private String message;
	private LocalDateTime createdAt;

	public static ChatDto from(Chat chat) {
		return new ChatDto(
			chat.getType(),
			chat.getMember().getNickname(),
			chat.getMember().getEmail(),
			chat.getRoom().getRoomId(),
			chat.getMessage(),
			chat.getCreatedAt()
		);
	}
}