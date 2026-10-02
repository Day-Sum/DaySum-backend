package com.jung.daysum.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ItunesDto {

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchResponse {

        private Integer resultCount;
        private List<Result> results;
    }


    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        private Long trackId;
        private String trackName;
        private String artistName;
        private String artworkUrl100;
        private String trackViewUrl;
        private String previewUrl;
    }
}
