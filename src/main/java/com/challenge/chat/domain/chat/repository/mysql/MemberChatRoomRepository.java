package com.challenge.chat.domain.chat.repository.mysql;

import java.util.Optional;

import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import com.challenge.chat.domain.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {

	Optional<MemberChatRoom> findByMemberAndRoom(Member member, ChatRoom room);
}
