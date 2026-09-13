package com.jung.daysum.response.exeption;

import com.jung.daysum.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception400 extends CustomException {

    public Exception400(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }

    public static class UserBadRequest extends Exception400 {
        public UserBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_USER, message);
        }
    }

    public static class TokenBadRequest extends Exception400 {
        public TokenBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_TOKEN, message);
        }
    }

    public static class CoupleBadRequest extends Exception400 {
        public CoupleBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_COUPLE, message);
        }
    }

    public static class ConnectCodeBadRequest extends Exception400 {
        public ConnectCodeBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_CONNECT_CODE, message);
        }
    }
}
