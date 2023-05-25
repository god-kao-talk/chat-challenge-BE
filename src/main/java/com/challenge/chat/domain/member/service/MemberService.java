package com.challenge.chat.domain.member.service;

import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberDto> getMemberList(){
        log.info("Service 멤버 리스트 조회");
        List<MemberDto> memberDtoList = memberRepository.findAll()
                .stream()
                .map(
                member -> {
                    Long id = member.getId();
                    String email = member.getEmail();
                    String imageUrl = member.getImageUrl();
                    String nickname = member.getNickname();
                    return new MemberDto(id, email, imageUrl, nickname);
                })
                .collect(Collectors.toList());
        return memberDtoList;
    }

    public MemberDto getMemberInfo(String email) {
        log.info("Service 멤버 단일 조회");
        Member member = getMemberByEmail(email);
        MemberDto memberDto = new MemberDto(member.getId(), member.getEmail(), member.getImageUrl(), member.getNickname());
        return memberDto;
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }
}
