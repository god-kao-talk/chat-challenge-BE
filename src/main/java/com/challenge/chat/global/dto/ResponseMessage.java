package com.challenge.chat.global.dto;

public class ResponseMessage {
	public static final String EXPIRED_TOKEN = "만료된 토큰입니다.";
	public static final String NOT_FOUND_TOKEN = "토큰을 찾을 수 없습니다.";
	public static final String LOGIN_SUCCESS = "로그인 성공";
	public static final String LOGIN_FAIL_ID = "로그인 실패 : 아이디를 확인해주세요.";
	public static final String LOGIN_FAIL_PASSWORD = "로그인 실패 : 비밀번호를 확인해주세요.";
	public static final String LOGOUT_SUCCESS = "로그아웃 성공";
	public static final String LOGOUT_FAIL = "이미 로그아웃 했습니다.";
	public static final String FOUND_USER_SUCCESS = "회원 정보 조회 성공";
	public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
	public static final String ALREADY_ENROLLED_USER = "이미 가입한 아이디입니다.";
	public static final String ALREADY_ENROLLED_NICKNAME = "이미 가입한 닉네임입니다.";
	public static final String CREATED_USER = "회원 가입 성공";
	public static final String CREATED_USER_FAIL = "회원 가입 실패";
	public static final String UPDATE_USER = "회원 정보 수정 성공";
	public static final String DELETE_USER = "회원 탈퇴 성공";
	public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
	public static final String DB_ERROR = "데이터베이스 에러";
	public static final String WRONG_ACCESS = "잘못된 접근입니다.";
	public static final String WRONG_FORMAT = "형식에 맞추어 작성해주세요.";
	public static final String S3_ERROR = "S3 이미지 저장 에러";
	public static final String BOARD_GET = "게시글 조회 성공";
	public static final String BOARD_GET_FAIL = "게시글이 비이었습니다.";
	public static final String BOARD_GET_ID = "특정게시글 조회 성공";
	public static final String BOARD_GET_FAIL_ID = "특정게시글 조회 실패";
	public static final String BOARD_CREATE = "게시글 작성 성공";
	public static final String BOARD_CREATE_FAIL = "게시글 작성 실패";
	public static final String BOARD_DELETE = "게시글 삭제 성공";
	public static final String BOARD_DELETE_FAIL = "게시글 삭제 실패";
	public static final String BOARD_UPDATE = "게시글 업데이트 성공";
	public static final String BOARD_UPDATE_FAIL = "게시글 업데이트 실패";
	public static final String COMMENT_CREATE = "댓글 작성 성공";
	public static final String COMMENT_CREATE_FAIL = "댓글 작성 실패";
	public static final String COMMENT_UPDATE = "댓글 수정 성공";
	public static final String COMMENT_UPDATE_FAIL = "댓글 수정 실패";
	public static final String COMMENT_DELETE = "댓글 삭제 성공";
	public static final String COMMENT_DELETE_FAIL = "댓글 삭제 실패";

}