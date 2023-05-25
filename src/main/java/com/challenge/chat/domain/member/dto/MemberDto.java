package com.challenge.chat.domain.member.dto;

import com.challenge.chat.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String email;
    private String imageUrl;
    private String nickname;

    public MemberDto(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.imageUrl = member.getImageUrl();
        this.nickname = member.getNickname();
    }
}
