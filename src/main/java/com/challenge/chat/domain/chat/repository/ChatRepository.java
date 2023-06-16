package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.Chat;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface ChatRepository extends CassandraRepository<Chat, Long> {

    List<Chat> findByRoomId(String roomId);
}
