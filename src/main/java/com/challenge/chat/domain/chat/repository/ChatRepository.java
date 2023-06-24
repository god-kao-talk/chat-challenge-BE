package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.ChatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByRoom(ChatRoom room);

    @Query("SELECT c FROM Chat c WHERE c.room.id = :roomId AND c.message LIKE %:message%")
    Optional<List<Chat>> findByRoomAndMessage(@Param("roomId") Long roomId, @Param("message") String message);
}
