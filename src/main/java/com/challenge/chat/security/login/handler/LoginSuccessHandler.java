package com.challenge.chat.security.login.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.challenge.chat.domain.member.repository.MemberCustomRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.challenge.chat.domain.member.repository.MemberCustomRepository;
import com.challenge.chat.security.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final MemberCustomRepository memberCustomRepository;
//	private final MemberRepository memberRepository;

	@Value("${jwt.access.expiration}")
	private String accessTokenExpiration;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) {
		String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
		String accessToken = jwtService.createAccessToken(email); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
		String refreshToken = jwtService.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

		jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

		// memberRepository.findByEmail(email)
		// 	.ifPresent(user -> {
		// 		user.updateRefreshToken(refreshToken);
		// 		memberRepository.save(user);
		// 	});
		log.info("로그인에 성공하였습니다. 이메일 : {}", email);
		log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
		log.info("로그인에 성공하였습니다. RefreshToken : {}", refreshToken);
		log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
	}

	private String extractUsername(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}
}
