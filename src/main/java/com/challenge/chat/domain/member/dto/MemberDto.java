package com.challenge.chat.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

    private String email;
    private String nickname;
    private String imageUrl;

    public MemberDto(String email, String nickname, String imageUrl){
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
