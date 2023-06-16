package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.ChatRoom;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends CassandraRepository<ChatRoom, String> {

	Optional<ChatRoom> findByRoomId(String roomId);
	List<ChatRoom> findByRoomIdIn(List<String> roomIds);
}
