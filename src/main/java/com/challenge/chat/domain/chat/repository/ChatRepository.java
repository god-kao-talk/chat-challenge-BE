package com.challenge.chat.domain.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.chat.domain.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	List<Chat> findAllByRoomOrderByCreatedAtAsc(Long RoomId);
}
