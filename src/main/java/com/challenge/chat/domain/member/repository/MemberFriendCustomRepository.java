package com.challenge.chat.domain.member.repository;


import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.MemberFriend;

import java.util.List;
import java.util.Optional;

public interface MemberFriendCustomRepository {

    Optional<MemberFriend> findByMemberAndFriend(Member member, Member friend);
    Optional<List<MemberFriend>> findByMember(Member member);
    void MemberFriendSave(MemberFriend memberFriend);
}
