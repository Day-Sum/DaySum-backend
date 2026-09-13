package com.jung.daysum.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = resolveToken(request);

        if(StringUtils.hasText(jwt)) {

            if(tokenProvider.validateToken(jwt)) {

                Authentication authentication =
                        tokenProvider.getAuthentication(jwt);

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

            else if(tokenProvider.isExpiredToken(jwt)) {

                throw new JwtException(
                        "토큰 만료 - ExpiredJwtException"
                );
            }

            else {

                throw new JwtException(
                        "잘못된 JWT 토큰입니다."
                );
            }
        }

        filterChain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {

        String bearerToken =
                request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(BEARER_PREFIX)) {

            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }


    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) throws ServletException {

        String[] excludePath = {
                "/reissue"
        };

        String path = request.getRequestURI();

        return Arrays.stream(excludePath)
                .anyMatch(path::startsWith);
    }
}