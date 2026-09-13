package com.jung.daysum.controller;

import com.jung.daysum.dto.AuthDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PutMapping("/signup")
    @Operation(summary = "회원가입 [JWT O]")
    public ResponseEntity<ResponseData<AuthDto.SignupResponse>> signup(@RequestBody AuthDto.SignupRequest signupRequestDto) {
        AuthDto.SignupResponse signupResponseDto = authService.signup(signupRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_USER, signupResponseDto);
    }

    @DeleteMapping("/users")
    @Operation(summary = "회원탈퇴 [JWT O]")
    public ResponseEntity<ResponseData> withdrawal() {
        authService.withdrawal();
        return ResponseData.toResponseEntity(ResponseCode.DELETE_USER);
    }

    @PostMapping("/reissue")
    @Operation(summary = "로그인 유지 - JWT Access Token 재발급 [JWT X]")
    public ResponseEntity<ResponseData<AuthDto.TokenResponse>> reissue(@RequestBody AuthDto.ReissueRequest reissueRequestDto) {
        AuthDto.TokenResponse tokenResponseDto = authService.reissue(reissueRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.REISSUE_SUCCESS, tokenResponseDto);
    }
}
