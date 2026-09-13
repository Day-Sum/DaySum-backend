package com.jung.daysum.response.exeption;

import com.jung.daysum.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception409 extends CustomException {

    public Exception409(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }

    public static class ConflictCoupleRecord extends Exception409 {
        public ConflictCoupleRecord(String message) {
            super(ResponseCode.CONFLICT_COUPLE_RECORD, message);
        }
    }
}