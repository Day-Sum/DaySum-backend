package com.jung.daysum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class HomeDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String mood;

        private String photoObjectKey;

        private String drawingObjectKey;

        private Boolean diaryWritten;

        private Music music;

        private CurrentActivity currentActivity;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Music {

        private String provider;

        private String trackId;

        private String title;

        private String artist;

        private String artworkUrl;

        private String storeUrl;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentActivity {

        private Long activityId;

        private String content;

        private LocalDateTime startedAt;
    }
}