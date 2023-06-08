package com.challenge.chat.domain.member.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "memberfriend")
@Getter
@NoArgsConstructor

public class MemberFriend {

    @Id
    private String id;

    private String memberEmail;
    private String friendEmail;

    public MemberFriend(String memberEmail, String friendEmail) {
        this.memberEmail = memberEmail;
        this.friendEmail = friendEmail;
    }
}
