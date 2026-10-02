package com.jung.daysum.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jung.daysum.dto.ItunesDto;
import com.jung.daysum.response.exeption.Exception500;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItunesClient {

    private static final String SEARCH_URL = "https://itunes.apple.com/search";
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private final ObjectMapper objectMapper;

    public ItunesDto.SearchResponse search(
            String term,
            String country,
            String media,
            String entity,
            Integer limit
    ) {
        URI uri = buildUri(term, country, media, entity, limit);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("User-Agent", "Mozilla/5.0 (DaySum/1.0)")
                .header("Accept", "application/json, text/javascript, */*")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.error(
                        "iTunes search failed. status={}, uri={}, body={}",
                        response.statusCode(),
                        uri,
                        abbreviate(response.body())
                );
                throw new Exception500.MusicServer("iTunes 검색 서버 응답에 실패했습니다.");
            }

            ItunesDto.SearchResponse parsed = parse(response.body());

            log.info(
                    "iTunes search completed. term={}, country={}, resultCount={}",
                    term,
                    country,
                    parsed.getResultCount()
            );

            if (parsed.getResultCount() == null || parsed.getResultCount() == 0) {
                log.warn(
                        "iTunes returned no results. uri={}, body={}",
                        uri,
                        abbreviate(response.body())
                );
            }

            return parsed;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception500.MusicServer("iTunes 검색 요청이 중단되었습니다.");
        } catch (IOException e) {
            log.error("iTunes search I/O error. uri={}", uri, e);
            throw new Exception500.MusicServer("iTunes 검색 서버에 연결하지 못했습니다.");
        }
    }

    private ItunesDto.SearchResponse parse(String body) {
        try {
            return objectMapper.readValue(body, ItunesDto.SearchResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse iTunes response. body={}", abbreviate(body), e);
            throw new Exception500.MusicServer("iTunes 검색 결과를 해석하지 못했습니다.");
        }
    }

    private URI buildUri(
            String term,
            String country,
            String media,
            String entity,
            Integer limit
    ) {
        String encodedTerm = URLEncoder.encode(term, StandardCharsets.UTF_8);

        String url = SEARCH_URL
                + "?term=" + encodedTerm
                + "&country=" + encode(country.toLowerCase())
                + "&media=" + encode(media)
                + "&entity=" + encode(entity)
                + "&limit=" + limit;

        return URI.create(url);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String abbreviate(String value) {
        if (value == null) {
            return "null";
        }

        int maxLength = 500;
        return value.length() <= maxLength
                ? value
                : value.substring(0, maxLength) + "...";
    }
}
