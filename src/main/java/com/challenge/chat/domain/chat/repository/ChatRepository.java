package com.challenge.chat.domain.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.challenge.chat.domain.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query("select distinct C from Chat C left join fetch C.member where C.room.id = ?1 order by C.createdAt")
	List<Chat> findAllByRoomIdOrderByCreatedAtAsc(@Param("id") Long id);
}
