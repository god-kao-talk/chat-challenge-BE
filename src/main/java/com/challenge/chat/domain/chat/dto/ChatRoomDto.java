package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
	private String roomCode;
	private String roomName;

	public static ChatRoomDto from(ChatRoom chatRoom) {
		return new ChatRoomDto(chatRoom.getRoomCode(), chatRoom.getRoomName());
	}
}
