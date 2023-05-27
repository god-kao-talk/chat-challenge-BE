package com.challenge.chat.domain.member.controller;

import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.dto.SignupDto;
import com.challenge.chat.domain.member.service.MemberService;
import com.challenge.chat.global.dto.ResponseDto;

import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/users")
    public List<MemberDto> getMemberList(){
        log.info("Controller 멤버 리스트 조회");
        return memberService.getMemberList();
    }

    @GetMapping("/users/myinfo")
    public MemberDto getMemberByEmail(@AuthenticationPrincipal User user){
        log.info("Controller 멤버 단일 조회");
        return memberService.getMemberByEmail(user.getUsername());
    }

    @GetMapping("/users/{userId}")
    public MemberDto getMemberByUserId(@PathVariable long userId){
        log.info("Controller 멤버 userId로 검색");
        return memberService.getMemberByUserId(userId);
    }

    @PostMapping("/users/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupDto signupDto ) throws Exception {
        memberService.signup(signupDto);
        return ResponseEntity.ok("회원가입 성공");
    }
}
