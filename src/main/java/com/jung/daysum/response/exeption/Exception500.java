package com.jung.daysum.response.exeption;

import com.jung.daysum.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception500 extends CustomException {

    public Exception500(ResponseCode errorResponseCode) {
        super(errorResponseCode, null);
    }

    public Exception500(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }


    public static class AnonymousUser extends Exception500 {

        public AnonymousUser(String message) {
            super(ResponseCode.ANONYMOUS_USER_ERROR, message);
        }
    }
}