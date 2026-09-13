package com.jung.daysum.service.impl;

import com.jung.daysum.domain.User;
import com.jung.daysum.domain.enums.Role;
import com.jung.daysum.dto.AuthDto;
import com.jung.daysum.dto.UserDto;
import com.jung.daysum.jwt.TokenProvider;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.response.exeption.Exception404;
import com.jung.daysum.service.AuthService;
import com.jung.daysum.service.UserService;
import com.jung.daysum.util.SecurityUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public AuthDto.SignupResponse signup(AuthDto.SignupRequest signupRequestDto) {
        User user = userService.findLoginUser();

        if(!user.getRole().equals(Role.ROLE_GUEST)) {
            throw new Exception400.UserBadRequest("이미 가입완료 되어있는 사용자입니다.");
        }

        if(signupRequestDto.getNickname() != null) {
            user.updateName(signupRequestDto.getNickname());
        }

        user.updateRole();

        UserDto.Response userResponseDto = new UserDto.Response(user);

        AuthDto.TokenResponse tokenResponseDto =
                tokenProvider.generateAccessTokenByRefreshToken(
                        user.getId(),
                        Role.ROLE_USER,
                        user.getRefreshToken()
                );

        AuthDto.SignupResponse signupResponseDto =
                AuthDto.SignupResponse.builder()
                        .userResponseDto(userResponseDto)
                        .tokenResponseDto(tokenResponseDto)
                        .build();

        return signupResponseDto;
    }


    @Transactional
    @Override
    public void withdrawal() {
        User user = userService.findLoginUser();

        user.deleteAccount();
    }
    @Transactional
    @Override
    public AuthDto.TokenResponse reissue(AuthDto.ReissueRequest reissueRequestDto) {  // Refresh Token으로 Access Token 재발급 메소드

        String accessToken = reissueRequestDto.getAccessToken();
        String refreshToken = reissueRequestDto.getRefreshToken();

        if(tokenProvider.validateToken(refreshToken) == false) {
            throw new JwtException("입력한 Refresh Token은 잘못된 토큰입니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        Long userId = Long.valueOf(authentication.getName());

        User user = userService.findUser(userId);
        Role role = user.getRole();

        if(user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            throw new Exception400.TokenBadRequest("Refresh Token = " + refreshToken);
        }

        AuthDto.TokenResponse tokenResponseDto = tokenProvider.generateAccessTokenByRefreshToken(userId, role, refreshToken);
        return tokenResponseDto;
    }

    @Transactional
    @Override
    public void updateRefreshToken(Long userId, String refreshToken) {
        User user = userService.findUser(userId);
        user.updateRefreshToken(refreshToken);
    }
}
