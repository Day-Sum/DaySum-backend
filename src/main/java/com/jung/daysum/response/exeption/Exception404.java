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
}
