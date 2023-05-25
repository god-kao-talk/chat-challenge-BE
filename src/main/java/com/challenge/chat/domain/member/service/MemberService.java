package com.challenge.chat.domain.member.service;

import com.challenge.chat.domain.member.dto.MemberDto;
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
                    String email = member.getEmail();
                    String imageUrl = member.getImageUrl();
                    String nickname = member.getNickname();
                    return new MemberDto(email, imageUrl, nickname);
                })
                .collect(Collectors.toList());
        return memberDtoList;
    }
}
