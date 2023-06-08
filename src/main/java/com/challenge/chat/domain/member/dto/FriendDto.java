package com.challenge.chat.domain.member.dto;

import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.MemberFriend;
import lombok.Getter;

@Getter
public class FriendDto {

    private String id;
    private String email;
    private String imageUrl;
    private String nickname;


    private FriendDto(String id, String email, String imageUrl, String nickname) {
        this.id = id;
        this.email = email;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }


    private FriendDto(String email){
        this.email = email;
    }


    public static FriendDto from(Member member, MemberFriend friend) {
        return new FriendDto(
                member.getId(),
                member.getEmail(),
                member.getImageUrl(),
                member.getNickname()
        );
    }
}