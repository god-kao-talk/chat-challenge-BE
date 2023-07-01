package com.challenge.chat.domain.member.service;

import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.dto.request.SignupRequest;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.MemberFriend;
import com.challenge.chat.domain.member.repository.MemberFriendRepository;
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
    private final MemberFriendRepository memberFriendRepository;
    private final PasswordEncoder passwordEncoder;

    public void addFriend(final String memberEmail, final String friendEmail) {

        Member member = findMemberByEmail(memberEmail);
        Member friend = findMemberByEmail(friendEmail);

        if (memberFriendRepository.findByMemberAndFriend(member, friend).isPresent()) {
            throw new RestApiException(MemberErrorCode.ADDED_FRIEND);
        }
        memberFriendRepository.save(MemberFriend.of(member, friend));
    }

    @Transactional(readOnly = true)
    public List<MemberDto> searchFriendList(final String memberEmail) {

        Member member = findMemberByEmail(memberEmail);

        return member.getFriendList()
            .stream()
            .map(a -> MemberDto.from(a.getFriend()))
            .collect(Collectors.toList());
    }

    public void signup(final SignupRequest signupRequest) {

        if (memberRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RestApiException(MemberErrorCode.DUPLICATED_EMAIL);
        }

        Member member = Member.builder()
            .email(signupRequest.getEmail())
            .password(passwordEncoder.encode(signupRequest.getPassword()))
            .nickname(signupRequest.getNickname())
            .role(MemberRole.USER)
            .build();

        memberRepository.save(member);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
