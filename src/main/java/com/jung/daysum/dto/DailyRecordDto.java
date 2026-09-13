package com.jung.daysum.dto;

import com.jung.daysum.domain.DailyRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class DailyRecordDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class MoodUpdateRequest {

        private String mood;
    }

    @Getter
    @NoArgsConstructor
    public static class DiaryUpdateRequest {

        private String diaryContent;
    }

    @Getter
    @NoArgsConstructor
    public static class DiaryShareRequest {

        private Boolean shared;
    }


    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoodResponse {

        private String mood;
        private Long version;
    }

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long dailyRecordId;
        private LocalDate recordDate;
        private String mood;
        private String diaryContent;
        private Boolean diaryShared;
        private Long version;

        public Response(DailyRecord entity) {
            this.dailyRecordId = entity.getId();
            this.recordDate = entity.getRecordDate();
            this.mood = entity.getMood();
            this.diaryContent = entity.getDiaryContent();
            this.diaryShared = entity.getDiarySharedCoupleId() != null;
            this.version = entity.getVersion();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiaryResponse {

        private String diaryContent;
        private Long version;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiaryShareResponse {

        private Boolean shared;
        private Long version;
    }
}