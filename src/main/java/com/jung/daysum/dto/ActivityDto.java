package com.jung.daysum.dto;

import com.jung.daysum.domain.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ActivityDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {

        private String content;
    }


    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long activityId;
        private String content;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Boolean current;

        public Response(Activity entity) {
            this.activityId = entity.getId();
            this.content = entity.getContent();
            this.startedAt = entity.getStartedAt();
            this.endedAt = entity.getEndedAt();
            this.current = entity.getEndedAt() == null;
        }
    }
}