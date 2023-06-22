package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {
    // Spring Data MongoDB -> https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/

    Optional<List<Chat>> findByRoomCode(String roomCode);
}
