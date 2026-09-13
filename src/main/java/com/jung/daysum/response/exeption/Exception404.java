package com.jung.daysum.response.exeption;

import com.jung.daysum.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception404 extends CustomException {

    public Exception404(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }

    public static class NoSuchUser extends Exception404 {
        public NoSuchUser(String message) {
            super(ResponseCode.NOT_FOUND_USER, message);
        }
    }

    public static class NoSuchCouple extends Exception404 {
        public NoSuchCouple(String message) {
            super(ResponseCode.NOT_FOUND_COUPLE, message);
        }
    }

    public static class NoSuchConnectCode extends Exception404 {
        public NoSuchConnectCode(String message) {
            super(ResponseCode.NOT_FOUND_CONNECT_CODE, message);
        }
    }

    public static class NoSuchDailyRecord extends Exception404 {

        public NoSuchDailyRecord(String message) {
            super(ResponseCode.NOT_FOUND_DAILY_RECORD, message);
        }
    }
}
