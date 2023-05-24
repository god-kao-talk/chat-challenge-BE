package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {

}
