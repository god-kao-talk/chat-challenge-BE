package com.challenge.chat.domain.chat.dto.response;

import java.time.Instant;

// import com.challenge.chat.domain.chat.entity.ChatES;
import com.challenge.chat.domain.chat.entity.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatSearchResponse {

	private MessageType type;
	private String nickname;
	private String email;
	private String roomId;
	private String message;
	private Instant createdAt;

	// public static ChatSearchResponse from(ChatES chatES) {
	// 	return new ChatSearchResponse(
	// 		chatES.getType(),
	// 		chatES.getSender(),
	// 		chatES.getUserId(),
	// 		chatES.getRoomId(),
	// 		chatES.getMessage(),
	// 		chatES.getCreatedAt()
	// 	);
	// }
}
