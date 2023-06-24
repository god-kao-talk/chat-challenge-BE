package com.challenge.chat.domain.chat.repository;

import java.util.List;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.ChatRoom;

public interface ChatCustomRepository {
	List<Chat> findByRoom(ChatRoom room);
	void chatSave(Chat chat);
}
