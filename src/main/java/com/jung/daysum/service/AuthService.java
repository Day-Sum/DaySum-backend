package com.jung.daysum.service;

import com.jung.daysum.dto.AuthDto;

public interface AuthService {
    AuthDto.SignupResponse signup(AuthDto.SignupRequest signupRequestDto);
    void withdrawal();
    AuthDto.TokenResponse reissue(AuthDto.ReissueRequest reissueRequestDto);
    void updateRefreshToken(Long userId, String refreshToken);
}
