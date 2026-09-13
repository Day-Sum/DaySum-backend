package com.jung.daysum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MusicDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        private String provider;
        private String trackId;
        private String title;
        private String artist;
        private String artworkUrl;
        private String storeUrl;
    }


    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchResponse {

        private List<MusicInfo> musics;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicInfo {

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
    public static class Response {

        private String provider;
        private String trackId;
        private String title;
        private String artist;
        private String artworkUrl;
        private String storeUrl;
        private Long version;
    }
}