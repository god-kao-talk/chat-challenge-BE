package com.challenge.chat.domain.chat.repository.mysql;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.chat.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	Optional<ChatRoom> findByRoomId(String roomId);

}
