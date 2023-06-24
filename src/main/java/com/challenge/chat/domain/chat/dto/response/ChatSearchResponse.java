package com.challenge.chat.domain.chat.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;

// import com.challenge.chat.domain.chat.entity.ChatES;
import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatSearchResponse {

	private MessageType type;
	private String nickname;
	private String email;
	private String roomCode;
	private String message;
	private LocalDateTime createdAt;


	public static ChatSearchResponse from(Chat chat) {
		return new ChatSearchResponse(
			chat.getType(),
			chat.getMember().getNickname(),
			chat.getMember().getEmail(),
			chat.getRoom().getRoomCode(),
			chat.getMessage(),
			chat.getCreatedAt()
		);
	}

	// public static ChatSearchResponse from(ChatES chatES) {
	// 	return new ChatSearchResponse(
	// 		chatES.getType(),
	// 		chatES.getNickname(),
	// 		chatES.getEmail(),
	// 		chatES.getRoomCode(),
	// 		chatES.getMessage(),
	// 		chatES.getCreatedAt()
	// 	);
	// }
}
