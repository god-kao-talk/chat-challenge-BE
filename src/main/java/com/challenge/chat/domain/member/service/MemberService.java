package com.challenge.chat.domain.member.service;

import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.dto.FriendDto;
import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.dto.SignupDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.MemberFriend;
import com.challenge.chat.domain.member.repository.MemberFriendRepository;
import com.challenge.chat.domain.member.repository.MemberRepository;

import com.challenge.chat.exception.RestApiException;
import com.challenge.chat.exception.dto.MemberErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberFriendRepository memberFriendRepository;
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;


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

    public void addFriend(User user, FriendDto friendDto) {
        if (memberFriendRepository.findByMemberEmailAndFriendEmail(user.getUsername(), friendDto.getEmail()).isEmpty()) {

            memberFriendRepository.save(new MemberFriend(user.getUsername(), friendDto.getEmail()));
        }
    }




    @Transactional(readOnly = true)
    public List<FriendDto> searchFriendList(final String memberEmail) {
        log.info("Service: 친구 리스트 조회");

        Optional<List<MemberFriend>> friendList = memberFriendRepository.findByMemberEmail(memberEmail);

        if (friendList.isPresent()) {
            List<MemberFriend> friends = friendList.get();
            List<String> friendEmails = friends.stream().map(MemberFriend::getFriendEmail).collect(Collectors.toList());

            Query query = new Query(Criteria.where("email").in(friendEmails));
            List<Member> memberList = mongoTemplate.find(query, Member.class);

            return memberList.stream()
                    //memberList스트림으로 변환(mongodb쿼리로 가져온 member객체 포함)
                    .map(member -> {
                        MemberFriend friend = friends.stream()
                                //Member객체에 해당하는 MemberFriend객체 가져옴
                                .filter(f -> f.getFriendEmail().equals(member.getEmail()))
                                //필터링 : Member객체의 이메일과 일치하는 friendEmail을 가진 MemberFriend객체 찾음
                                .findFirst()
                                //필터링 된 스트림에서 첫 번째 일치하는 MemberFriend객체를 가져옴
                                .orElse(null);
                                //없으면 Null
                        return FriendDto.from(member, friend);
                        //Member 객체와 해당하는 MemberFriend객체를 사용하여 FriendDto 객체 생성
                    })
                    .collect(Collectors.toList());
                    //생성된 FriendDto객체를 리스트화
        } else {
            return Collections.emptyList();
        }
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
