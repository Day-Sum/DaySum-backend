package com.jung.daysum.oauth.handler;

import com.jung.daysum.domain.enums.Role;
import com.jung.daysum.dto.AuthDto;
import com.jung.daysum.jwt.TokenProvider;
import com.jung.daysum.oauth.CustomOAuth2User;

import com.jung.daysum.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            Long userId = oAuth2User.getUserId();
            Role role = oAuth2User.getRole();

            AuthDto.TokenResponse tokenResponseDto = tokenProvider.generateTokenDto(userId, role);
            String refreshToken = tokenResponseDto.getRefreshToken();

            // 로그인에 성공했으므로, 사용자 DB에 Refresh Token 저장(있다면 업데이트).
            authService.updateRefreshToken(userId, refreshToken);

            String redirectUrl;
            if(oAuth2User.getRole().equals(Role.ROLE_GUEST)) {
                redirectUrl = makeRedirectUrl(tokenResponseDto, true);
                log.info("신규 회원 입니다. JWT 헤더를 가진채로, 추가정보 입력을 위한 회원가입 페이지로 리다이렉트 시켜주세요.");
            }
            else {
                redirectUrl = makeRedirectUrl(tokenResponseDto, false);
                log.info("기존 회원 입니다. JWT 헤더를 가진채로, 메인 페이지로 리다이렉트 시켜주세요.");
            }

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } catch (Exception e) {
            throw e;
        }
    }

    public String makeRedirectUrl(AuthDto.TokenResponse tokenResponseDto, boolean isNewUser) {
        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/loginwait")
                .queryParam("grantType", tokenResponseDto.getGrantType())
                .queryParam("accessToken", tokenResponseDto.getAccessToken())
                .queryParam("accessTokenExpiresIn", tokenResponseDto.getAccessTokenExpiresIn())
                .queryParam("refreshToken", tokenResponseDto.getRefreshToken())
                .queryParam("isNewUser", isNewUser)
                .build().toUriString();
        return redirectUrl;
    }
}
