package com.jung.daysum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PartnerDto {

    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodayResponse {

        private String mood;

        private String photoObjectKey;

        private String drawingObjectKey;

        private Boolean diaryShared;

        private Music music;

        private CurrentActivity currentActivity;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyRecordResponse {

        private Long dailyRecordId;

        private LocalDate recordDate;

        private String mood;

        private String photoObjectKey;

        private String drawingObjectKey;

        private String diaryContent;

        private Boolean diaryShared;

        private Music music;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityResponse {

        private Long activityId;

        private String content;

        private LocalDateTime startedAt;

        private LocalDateTime endedAt;
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