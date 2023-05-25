package com.challenge.chat.domain.member.service;

import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberDto> getMemberList(){
        log.info("Service 멤버 리스트 조회");

        return memberRepository.findAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }

    public MemberDto getMemberByEmail(String email) {
        log.info("Service 멤버 단일 조회");
        Member member = findMemberByEmail(email);
        return new MemberDto(member);
    }

    public MemberDto getMemberByUserId(long userId){
        log.info("Service 멤버 userId로 검색");
        Member member = findMemberById(userId);
        return new MemberDto(member);
    }

    // 공통 메서드
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 유저입니다."));
    }

    public Member findMemberById(long userId){
        return memberRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 유저입니다."));
    }
}
