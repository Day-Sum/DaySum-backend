package com.jung.daysum.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jung.daysum.domain.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ActivityDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        @JsonAlias("content")
        private String activity;
    }


    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class CurrentActivity {

        private Long activityId;
        private String activity;
        private LocalDateTime startedAt;

        public CurrentActivity(Activity entity) {
            this.activityId = entity.getId();
            this.activity = entity.getContent();
            this.startedAt = entity.getStartedAt();
        }
    }


    @Getter
    @NoArgsConstructor
    public static class SaveResponse {

        private CurrentActivity currentActivity;

        public SaveResponse(Activity entity) {
            this.currentActivity = new CurrentActivity(entity);
        }
    }


    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long activityId;
        private String activity;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Boolean current;

        public Response(Activity entity) {
            this.activityId = entity.getId();
            this.activity = entity.getContent();
            this.startedAt = entity.getStartedAt();
            this.endedAt = entity.getEndedAt();
            this.current = entity.getEndedAt() == null;
        }
    }
}
