package com.challenge.chat.domain.member.controller;

import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.dto.request.SignupRequest;
import com.challenge.chat.domain.member.dto.request.MemberAddRequest;
import com.challenge.chat.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/users/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid final SignupRequest signupRequest) {
        memberService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    @PostMapping("/users/friend")
    public ResponseEntity<String> addFriend(
        @AuthenticationPrincipal final User user,
        @RequestBody @Valid final MemberAddRequest memberAddRequest) {
        memberService.addFriend(user.getUsername(), memberAddRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("친구추가 성공");
    }


    @GetMapping("/users/friend")
    public ResponseEntity<List<MemberDto>> getFriendList(@AuthenticationPrincipal final User user) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(memberService.searchFriendList(user.getUsername()));
    }
}
