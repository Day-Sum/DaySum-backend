package com.jung.daysum.response.responseitem;

public class MessageItem {

    // < User >
    public static final String CREATED_USER = "SUCCESS - 회원 가입 성공";
    public static final String READ_USER = "SUCCESS - 회원 정보 조회 성공";
    public static final String DELETE_USER = "SUCCESS - 회원 탈퇴 성공";
    public static final String NOT_FOUND_USER = "ERROR - 존재하지 않는 회원 조회 에러";
    public static final String BAD_REQUEST_USER = "ERROR - 잘못된 회원 요청 에러";

    // < Auth >
    public static final String UNAUTHORIZED = "ERROR - Unauthorized 에러";
    public static final String FORBIDDEN = "ERROR - Forbidden 에러";

    // < Token >
    public static final String REISSUE_SUCCESS = "SUCCESS - JWT Access 토큰 재발급 성공";
    public static final String TOKEN_EXPIRED = "ERROR - JWT 토큰 만료 에러";
    public static final String TOKEN_ERROR = "ERROR - 잘못된 JWT 토큰 에러";
    public static final String BAD_REQUEST_TOKEN = "ERROR - 잘못된 토큰 요청 에러";

    // < Couple >
    public static final String READ_COUPLE_STATUS = "SUCCESS - 연인 연결 여부 조회 성공";
    public static final String READ_CONNECT_CODE = "SUCCESS - 연인 연결 코드 조회 성공";
    public static final String REISSUE_CONNECT_CODE = "SUCCESS - 연인 연결 코드 재발급 성공";
    public static final String CREATED_COUPLE = "SUCCESS - 연인 연결 성공";
    public static final String READ_COUPLE = "SUCCESS - 커플 정보 조회 성공";
    public static final String UPDATE_COUPLE_START_DATE = "SUCCESS - 연애 시작일 수정 성공";
    public static final String DELETE_COUPLE = "SUCCESS - 연인 연결 해제 성공";

    public static final String NOT_FOUND_COUPLE = "ERROR - 존재하지 않는 커플 관계 조회 에러";
    public static final String NOT_FOUND_CONNECT_CODE = "ERROR - 존재하지 않는 연결 코드 조회 에러";
    public static final String BAD_REQUEST_COUPLE = "ERROR - 잘못된 커플 요청 에러";
    public static final String BAD_REQUEST_CONNECT_CODE = "ERROR - 잘못된 연결 코드 요청 에러";

    // < Etc >
    public static final String ANONYMOUS_USER_ERROR = "ERROR - anonymousUser 에러";
    public static final String INTERNAL_SERVER_ERROR = "ERROR - 서버 내부 에러";
}
