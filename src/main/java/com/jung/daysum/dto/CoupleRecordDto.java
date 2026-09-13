package com.jung.daysum.dto;

import com.jung.daysum.domain.CoupleRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CoupleRecordDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        private String content;
        private Long version;

    }


    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class Response {

        private String content;
        private Long version;
        private Long updatedByUserId;
        private LocalDateTime updatedAt;

        public Response(CoupleRecord entity) {
            this.content = entity.getContent();
            this.version = entity.getVersion();
            this.updatedByUserId = entity.getUpdatedByUserId();
            this.updatedAt = entity.getModifiedTime();
        }
    }
}