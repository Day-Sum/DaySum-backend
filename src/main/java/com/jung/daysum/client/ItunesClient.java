package com.jung.daysum.client;

import com.jung.daysum.config.FeignConfig;
import com.jung.daysum.dto.ItunesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "itunes",
        url = "https://itunes.apple.com",
        configuration = FeignConfig.class
)
public interface ItunesClient {

    @GetMapping("/search")
    ItunesDto.SearchResponse search(
            @RequestParam("term") String term,
            @RequestParam("country") String country,
            @RequestParam("media") String media,
            @RequestParam("entity") String entity,
            @RequestParam("limit") Integer limit
    );
}