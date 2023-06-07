package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.dto.request.ChatRoomAddRequest;
import com.challenge.chat.domain.chat.dto.request.ChatRoomCreateRequest;
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
	private String roomId;
	private String roomName;

	public static ChatRoomDto from(ChatRoomCreateRequest chatRoomCreateRequest) {
		return new ChatRoomDto(null, chatRoomCreateRequest.getRoomName());
	}

	public static ChatRoomDto from(ChatRoomAddRequest chatRoomAddRequest) {
		return new ChatRoomDto(chatRoomAddRequest.getRoomId(), null);
	}

	public static ChatRoomDto from(ChatRoom chatRoom) {
		return new ChatRoomDto(chatRoom.getRoomId(), chatRoom.getRoomName());
	}

	public static ChatRoom toEntity(ChatRoomDto dto) {
		return ChatRoom.of(
			UUID.randomUUID().toString(),
			dto.getRoomName()
		);
	}
}
