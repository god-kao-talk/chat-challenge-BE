package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import com.challenge.chat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChatRoomCustomRepository {

    Optional<MemberChatRoom> findByMemberAndRoom(Member member, ChatRoom room);

    Optional<List<MemberChatRoom>> findByMember(Member member);
    void MemberChatRoomSave(MemberChatRoom memberChatRoom);


}
