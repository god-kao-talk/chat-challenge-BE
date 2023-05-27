package com.challenge.chat.domain.chat.dto;

import java.util.UUID;

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
	private Long id;
	private String roomId;
	private String roomName;

	public static ChatRoomDto from(ChatRoom chatRoom) {
		return new ChatRoomDto(chatRoom.getId(), chatRoom.getRoomId(), chatRoom.getRoomName());
	}

	public static ChatRoom toEntity(ChatRoomDto dto) {
		return ChatRoom.of(
			UUID.randomUUID().toString(),
			dto.getRoomName()
		);
	}
}
