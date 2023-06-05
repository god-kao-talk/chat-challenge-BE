package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.MessageType;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	private String message;
	@Builder.Default
	private String date = formatDate();

	// TODO: 사용하지 않는 생성자 삭제여부
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
				chat.getSender(),
				chat.getUserId(),
				chat.getRoomId(),
				chat.getMessage()
		);
	}

	public static String formatDate(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
}