package com.jung.daysum.response;

import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.response.exeption.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseData> customException(CustomException e) {
        return ResponseData.toResponseEntity(e.getErrorResponseCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData> exception(Exception e) {
        return ResponseData.toResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
    }
}
