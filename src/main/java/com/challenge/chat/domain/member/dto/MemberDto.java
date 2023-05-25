package com.challenge.chat.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

    private String email;
    private String imageUrl;
    private String nickname;

    public MemberDto(String email, String imageUrl, String nickname){
        this.email = email;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }
}
