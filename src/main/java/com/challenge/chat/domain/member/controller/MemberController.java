package com.challenge.chat.domain.member.controller;

import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.dto.SignupDto;
import com.challenge.chat.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/users")
    public ResponseEntity<List<MemberDto>> getMemberList() {
        log.info("Controller 멤버 리스트 조회");
        return ResponseEntity.status(HttpStatus.OK)
            .body(memberService.getMemberList());
    }

    @GetMapping("/users/myinfo")
    public ResponseEntity<MemberDto> getMemberByEmail(@AuthenticationPrincipal User user) {
        log.info("Controller 멤버 단일 조회");
        return ResponseEntity.status(HttpStatus.OK)
            .body(memberService.getMemberByEmail(user.getUsername()));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<MemberDto> getMemberByUserId(@PathVariable long userId) {
        log.info("Controller 멤버 userId로 검색");
        return ResponseEntity.status(HttpStatus.OK)
            .body(memberService.getMemberByUserId(userId));
    }

    @PostMapping("/users/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupDto signupDto) {
        memberService.signup(signupDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }
}
