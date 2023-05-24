package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.ChatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDto {
	private Long id;
	private String roomId;
	private String roomName;

	public ChatRoomDto(ChatRoom chatRoom) {
		this.id = chatRoom.getId();
		this.roomId = chatRoom.getRoomId();
		this.roomName = chatRoom.getRoomName();
	}
}
