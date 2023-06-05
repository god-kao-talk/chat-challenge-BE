package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

	Optional<ChatRoom> findByRoomId(String roomId);

}
