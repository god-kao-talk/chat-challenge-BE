package com.challenge.chat.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "member_friend")
@Getter
@NoArgsConstructor
public class MemberFriend {

    @PrimaryKeyColumn(value = "email", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String memberEmail;
    @PrimaryKeyColumn(name = "friend_email", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING, ordinal = 1)
    private String friendEmail;

    public MemberFriend(String memberEmail, String friendEmail) {
        this.memberEmail = memberEmail;
        this.friendEmail = friendEmail;
    }
}
