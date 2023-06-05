package com.challenge.chat.domain.member.service;

import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.dto.SignupDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.repository.MemberRepository;

import com.challenge.chat.exception.RestApiException;
import com.challenge.chat.exception.dto.MemberErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberDto> getMemberList() {
        log.info("Service 멤버 리스트 조회");

        return memberRepository.findAll()
                .stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDto getMemberByEmail(String email) {
        log.info("Service 멤버 단일 조회");
        return MemberDto.from(findMemberByEmail(email));
    }

    @Transactional(readOnly = true)
    public MemberDto getMemberByUserId(String userId) {
        log.info("Service 멤버 userId로 검색");
        return MemberDto.from(findMemberById(userId));
    }

    public void signup(SignupDto signupDto) {

        if (memberRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new RestApiException(MemberErrorCode.DUPLICATED_EMAIL);
        }

        Member member = Member.builder()
            .email(signupDto.getEmail())
            .password(passwordEncoder.encode(signupDto.getPassword()))
            .nickname(signupDto.getNickname())
            .role(MemberRole.USER)
            .build();

        memberRepository.save(member);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findMemberById(String userId) {
        return memberRepository.findById(userId).orElseThrow(
            () -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
