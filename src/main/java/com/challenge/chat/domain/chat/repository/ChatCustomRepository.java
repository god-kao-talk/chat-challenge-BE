package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.ChatRoom;

import java.util.List;

public interface ChatCustomRepository {
    List<Chat> findByRoom(ChatRoom room);
    void chatSave(Chat chat);
}
