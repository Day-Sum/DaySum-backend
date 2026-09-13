package com.jung.daysum.dto;

import com.jung.daysum.domain.Couple;
import com.jung.daysum.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class CoupleDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class ConnectRequest {

        private String connectCode;
        private LocalDate relationshipStartedOn;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateStartDateRequest {

        private LocalDate relationshipStartedOn;
    }


    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusResponse {

        private Boolean connected;
        private Long coupleId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConnectCodeResponse {

        private String connectCode;
    }

    @Getter
    @NoArgsConstructor
    public static class ConnectResponse {

        private Long coupleId;
        private Long partnerUserId;
        private String partnerNickname;
        private LocalDate relationshipStartedOn;

        public ConnectResponse(Couple entity, User partner) {
            this.coupleId = entity.getId();
            this.partnerUserId = partner.getId();
            this.partnerNickname = partner.getNickname();
            this.relationshipStartedOn = entity.getStartedAt();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long coupleId;
        private Long partnerUserId;
        private String partnerNickname;
        private LocalDate relationshipStartedOn;
        private Long dayCount;

        public Response(Couple entity, User partner, Long dayCount) {
            this.coupleId = entity.getId();
            this.partnerUserId = partner.getId();
            this.partnerNickname = partner.getNickname();
            this.relationshipStartedOn = entity.getStartedAt();
            this.dayCount = dayCount;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StartDateResponse {

        private LocalDate relationshipStartedOn;
        private Long dayCount;
    }
}