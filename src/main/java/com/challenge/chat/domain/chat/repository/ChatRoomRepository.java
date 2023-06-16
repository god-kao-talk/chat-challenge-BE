package com.challenge.chat.domain.chat.repository;

import java.util.Optional;

import com.challenge.chat.domain.chat.entity.ChatRoom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	Optional<ChatRoom> findByRoomCode(String roomCode);
}
