package com.jung.daysum.response;

import com.jung.daysum.response.responseitem.MessageItem;
import com.jung.daysum.response.responseitem.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    // auth
    CREATED_USER(StatusItem.CREATED, MessageItem.CREATED_USER),
    READ_USER(StatusItem.OK, MessageItem.READ_USER),
    DELETE_USER(StatusItem.NO_CONTENT, MessageItem.DELETE_USER),

    NOT_FOUND_USER(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_USER),
    BAD_REQUEST_USER(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_USER),

    UNAUTHORIZED_ERROR(StatusItem.UNAUTHORIZED, MessageItem.UNAUTHORIZED),
    FORBIDDEN_ERROR(StatusItem.FORBIDDEN, MessageItem.FORBIDDEN),

    REISSUE_SUCCESS(StatusItem.OK, MessageItem.REISSUE_SUCCESS),

    TOKEN_EXPIRED(StatusItem.UNAUTHORIZED, MessageItem.TOKEN_EXPIRED),
    TOKEN_ERROR(StatusItem.UNAUTHORIZED, MessageItem.TOKEN_ERROR),
    BAD_REQUEST_TOKEN(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_TOKEN),

    ANONYMOUS_USER_ERROR(StatusItem.UNAUTHORIZED, MessageItem.ANONYMOUS_USER_ERROR),
    INTERNAL_SERVER_ERROR(StatusItem.INTERNAL_SERVER_ERROR, MessageItem.INTERNAL_SERVER_ERROR),

    // Couple
    READ_COUPLE_STATUS(StatusItem.OK, MessageItem.READ_COUPLE_STATUS),
    READ_CONNECT_CODE(StatusItem.OK, MessageItem.READ_CONNECT_CODE),
    REISSUE_CONNECT_CODE(StatusItem.OK, MessageItem.REISSUE_CONNECT_CODE),

    CREATED_COUPLE(StatusItem.CREATED, MessageItem.CREATED_COUPLE),
    READ_COUPLE(StatusItem.OK, MessageItem.READ_COUPLE),
    UPDATE_COUPLE_START_DATE(StatusItem.OK, MessageItem.UPDATE_COUPLE_START_DATE),
    DELETE_COUPLE(StatusItem.NO_CONTENT, MessageItem.DELETE_COUPLE),

    NOT_FOUND_COUPLE(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_COUPLE),
    NOT_FOUND_CONNECT_CODE(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_CONNECT_CODE),

    BAD_REQUEST_COUPLE(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_COUPLE),
    BAD_REQUEST_CONNECT_CODE(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_CONNECT_CODE),

    // DailyRecord
    UPDATE_DAILY_RECORD_MOOD(StatusItem.OK, MessageItem.UPDATE_DAILY_RECORD_MOOD),
    READ_DAILY_RECORD(StatusItem.OK, MessageItem.READ_DAILY_RECORD),
    UPDATE_DAILY_RECORD_DIARY(StatusItem.OK, MessageItem.UPDATE_DAILY_RECORD_DIARY),
    DELETE_DAILY_RECORD_DIARY(StatusItem.NO_CONTENT, MessageItem.DELETE_DAILY_RECORD_DIARY),
    UPDATE_DAILY_RECORD_DIARY_SHARE(StatusItem.OK, MessageItem.UPDATE_DAILY_RECORD_DIARY_SHARE),
    NOT_FOUND_DAILY_RECORD(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_DAILY_RECORD),
    BAD_REQUEST_DAILY_RECORD(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_DAILY_RECORD),
    ;

    private int httpStatus;
    private String message;
}