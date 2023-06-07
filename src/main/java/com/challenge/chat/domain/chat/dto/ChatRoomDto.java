package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.dto.request.ChatRoomRequest;
import com.challenge.chat.domain.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
	private String id;
	private String roomId;
	private String roomName;

	private ChatRoomDto(String roomName) {
		this.roomName = roomName;
	}

	public static ChatRoomDto from(ChatRoomRequest request) {
		return new ChatRoomDto(request.getRoomName());
	}

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
