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

    // < DailyRecord >
    public static final String UPDATE_DAILY_RECORD_MOOD = "SUCCESS - 오늘 기분 저장 성공";
    public static final String READ_DAILY_RECORD = "SUCCESS - 일일 기록 조회 성공";
    public static final String UPDATE_DAILY_RECORD_DIARY = "SUCCESS - 일기 저장 성공";
    public static final String DELETE_DAILY_RECORD_DIARY = "SUCCESS - 일기 삭제 성공";
    public static final String UPDATE_DAILY_RECORD_DIARY_SHARE = "SUCCESS - 일기 공유 상태 수정 성공";
    public static final String NOT_FOUND_DAILY_RECORD = "ERROR - 존재하지 않는 일일 기록 조회 에러";
    public static final String BAD_REQUEST_DAILY_RECORD = "ERROR - 잘못된 일일 기록 요청 에러";
    public static final String BAD_REQUEST_STORAGE = "ERROR - 잘못된 파일 요청 에러";
    public static final String STORAGE_ERROR = "ERROR - 파일 스토리지 처리 에러";
    public static final String UPDATE_DAILY_RECORD_PHOTO = "SUCCESS - 오늘 사진 저장 성공";
    public static final String DELETE_DAILY_RECORD_PHOTO = "SUCCESS - 오늘 사진 삭제 성공";
    public static final String UPDATE_DAILY_RECORD_DRAWING = "SUCCESS - 오늘 그림 저장 성공";
    public static final String DELETE_DAILY_RECORD_DRAWING = "SUCCESS - 오늘 그림 삭제 성공";
    public static final String BAD_REQUEST_MUSIC = "ERROR - 잘못된 음악 요청 에러";
    public static final String MUSIC_SERVER_ERROR = "ERROR - 음악 검색 서버 처리 에러";

    // < Music >
    public static final String READ_MUSIC_SEARCH = "SUCCESS - 음악 검색 성공";
    public static final String UPDATE_DAILY_RECORD_MUSIC = "SUCCESS - 오늘 음악 저장 성공";
    public static final String DELETE_DAILY_RECORD_MUSIC = "SUCCESS - 오늘 음악 삭제 성공";

    // < Activity >
    public static final String CREATED_ACTIVITY = "SUCCESS - 활동 시작 성공";
    public static final String DELETE_CURRENT_ACTIVITY = "SUCCESS - 현재 활동 종료 성공";
    public static final String READ_ACTIVITIES = "SUCCESS - 활동 기록 조회 성공";
    public static final String BAD_REQUEST_ACTIVITY = "ERROR - 잘못된 활동 요청 에러";
    public static final String NOT_FOUND_CURRENT_ACTIVITY = "ERROR - 진행 중인 활동 조회 에러";

    // < Etc >
    public static final String ANONYMOUS_USER_ERROR = "ERROR - anonymousUser 에러";
    public static final String INTERNAL_SERVER_ERROR = "ERROR - 서버 내부 에러";
}
