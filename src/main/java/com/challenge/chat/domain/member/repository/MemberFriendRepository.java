package com.challenge.chat.domain.member.repository;

import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.MemberFriend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberFriendRepository extends JpaRepository<MemberFriend, Long> {

    Optional<MemberFriend> findByMemberAndFriend(Member member, Member friend);
    Optional<List<MemberFriend>> findByMember(Member member);
}
