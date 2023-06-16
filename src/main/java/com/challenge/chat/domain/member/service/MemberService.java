package com.challenge.chat.domain.member.service;

import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.dto.SignupDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.MemberFriend;
import com.challenge.chat.domain.member.repository.MemberFriendRepository;
import com.challenge.chat.domain.member.repository.MemberRepository;

import com.challenge.chat.exception.RestApiException;
import com.challenge.chat.exception.dto.MemberErrorCode;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.querybuilder.BindMarker;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberFriendRepository memberFriendRepository;
    private final PasswordEncoder passwordEncoder;
    private final CassandraTemplate cassandraTemplate;


    // @Transactional(readOnly = true)
    // public List<MemberDto> getMemberList() {
    //     log.info("Service 멤버 리스트 조회");
    //
    //     return memberRepository.findAll()
    //             .stream()
    //             .map(MemberDto::from)
    //             .collect(Collectors.toList());
    // }

    // @Transactional(readOnly = true)
    // public MemberDto getMemberByEmail(String email) {
    //     log.info("Service 멤버 단일 조회");
    //     return MemberDto.from(findMemberByEmail(email));
    // }

    // @Transactional(readOnly = true)
    // public MemberDto getMemberByUserId(String userId) {
    //     log.info("Service 멤버 userId로 검색");
    //     return MemberDto.from(findMemberById(userId));
    // }
    //
    public void addFriend(final String memberEmail, final MemberDto memberDto) {
        log.info("Service: 친구 추가");

        if (memberFriendRepository.findByMemberEmailAndFriendEmail(memberEmail, memberDto.getEmail()).isEmpty()) {
            memberFriendRepository.save(new MemberFriend(memberEmail, memberDto.getEmail()));
        } else {
            throw new RestApiException(MemberErrorCode.DUPLICATED_MEMBER);
        }
    }

    public List<MemberDto> searchFriendList(final String memberEmail) {
        log.info("Service: 친구 리스트 조회");

        List<String> friendEmails = findFriendEmailList(memberEmail);

        Select selectQuery = QueryBuilder.selectFrom(CqlIdentifier.fromCql("member"))
            .all()
            .whereColumn(CqlIdentifier.fromCql("email"))
            .in((BindMarker)friendEmails);
        String cql = selectQuery.build().getQuery();
        return cassandraTemplate.select(cql, Member.class)
            .stream()
            .map(MemberDto::from)
            .collect(Collectors.toList());
    }

    public void signup(final SignupDto signupDto) {

        if (memberRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new RestApiException(MemberErrorCode.DUPLICATED_EMAIL);
        }

        Member member = Member.builder()
            .email(signupDto.getEmail())
            .password(passwordEncoder.encode(signupDto.getPassword()))
            .nickname(signupDto.getNickname())
            .role(MemberRole.USER)
            .createdAt(Instant.now())
            .build();

        memberRepository.save(member);
    }

    public List<String> findFriendEmailList(final String email) {
        List<MemberFriend> friendList = memberFriendRepository.findByMemberEmail(email).orElse(null);
        if (friendList == null) {
            return null;
        }
        return friendList.stream()
            .map(MemberFriend::getFriendEmail)
            .collect(Collectors.toList());
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
