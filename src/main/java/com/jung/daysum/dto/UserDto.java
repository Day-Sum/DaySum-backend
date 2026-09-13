package com.jung.daysum.dto;


import com.jung.daysum.domain.User;
import com.jung.daysum.domain.enums.SocialType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserDto {

    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long userId;
        private String email;
        private String nickname;
        private SocialType socialType;
        private LocalDateTime createdTime;

        public Response(User entity) {
            this.userId = entity.getId();
            this.email = entity.getEmail();
            this.nickname = entity.getNickname();
            this.socialType = entity.getSocialType();
            this.createdTime = entity.getCreatedTime();
        }
    }
}
