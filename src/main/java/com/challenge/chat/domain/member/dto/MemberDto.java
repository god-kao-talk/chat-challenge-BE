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

    private MemberDto(Long id, String email, String imageUrl, String nickname) {
        this.id = id;
        this.email = email;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }

    public static MemberDto from(Member member) {
        return new MemberDto(
            member.getId(),
            member.getEmail(),
            member.getImageUrl(),
            member.getNickname()
        );
    }
}
