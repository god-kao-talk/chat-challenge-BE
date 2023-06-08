package com.challenge.chat.domain.member.repository;

import com.challenge.chat.domain.member.entity.MemberFriend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MemberFriendRepository extends MongoRepository<MemberFriend, String> {

    Optional<MemberFriend> findByMemberEmailAndFriendEmail(String memberEmail, String friendEmail);
    Optional<List<MemberFriend>> findByMemberEmail(String memberEmail);
}
