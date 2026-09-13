package com.jung.daysum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ItunesDto {

    @Getter
    @NoArgsConstructor
    public static class SearchResponse {

        private Integer resultCount;
        private List<Result> results;
    }


    @Getter
    @NoArgsConstructor
    public static class Result {

        private Long trackId;
        private String trackName;
        private String artistName;
        private String artworkUrl100;
        private String trackViewUrl;
    }
}