package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomCustomRepository {

	Optional<ChatRoom> findByRoomCode(String roomCode);
	void chatRoomSave(ChatRoom chatRoom);

}
