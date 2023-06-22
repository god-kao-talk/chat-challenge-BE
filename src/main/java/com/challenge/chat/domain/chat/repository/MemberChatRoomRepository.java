package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChatRoomRepository extends MongoRepository<MemberChatRoom, String> {

	Optional<MemberChatRoom> findByEmailAndRoomCode(String email, String roomCode);

	Optional<List<MemberChatRoom>> findByEmail(String email);
}
