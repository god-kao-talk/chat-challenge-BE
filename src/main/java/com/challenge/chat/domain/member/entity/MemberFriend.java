package com.challenge.chat.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MemberFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "FRIEND_ID")
    private Member friend;

    private MemberFriend(Member member, Member friend) {
        this.member = member;
        this.friend = friend;
        // member.getFriendList().add(this);
    }

    public static MemberFriend of(Member member, Member friend) {
        return new MemberFriend(member, friend);
    }
}
