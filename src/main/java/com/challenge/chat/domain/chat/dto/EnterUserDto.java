package com.challenge.chat.domain.chat.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnterUserDto {

	private String sender;
	private String userId;
	private String roomId;
	private List<ChatDto> chatList;

	public EnterUserDto(String sender, String userId, String roomId, List<ChatDto> chatDtoList) {
		this.sender = sender;
		this.userId = userId;
		this.roomId = roomId;
		this.chatList = chatDtoList;
	}
}

