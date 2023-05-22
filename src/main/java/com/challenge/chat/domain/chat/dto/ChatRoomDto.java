package com.challenge.chat.domain.chat.dto;

import com.challenge.chat.domain.chat.entity.ChatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDto {
	private String roomId;
	private String roomName;
	private String host;
	private Long headCount;
	// private String profile_image;

	public ChatRoomDto(ChatRoom chatRoom) {
		this.roomId = chatRoom.getRoomId();
		this.roomName = chatRoom.getRoomName();
		this.host = chatRoom.getHost();
		// this.profile_image = profile_image;
	}

}
