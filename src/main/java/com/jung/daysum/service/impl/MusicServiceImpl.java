package com.jung.daysum.service.impl;

import com.jung.daysum.client.ItunesClient;
import com.jung.daysum.dto.ItunesDto;
import com.jung.daysum.dto.MusicDto;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final ItunesClient itunesClient;

    @Override
    public MusicDto.SearchResponse searchMusic(String query) {

        if (query == null || query.isBlank()) {
            throw new Exception400.MusicBadRequest(
                    "검색어가 입력되지 않았습니다."
            );
        }

        String normalizedQuery = query.trim();

        // 한국 스토어 우선 검색
        ItunesDto.SearchResponse itunesSearchResponse =
                itunesClient.search(
                        normalizedQuery,
                        "kr",
                        "music",
                        "song",
                        20
                );

        // 한국 스토어에서 0건이면 미국 스토어로 한 번 더 검색한다.
        // iTunes storefront/edge 응답 차이 때문에 검색 결과가 비는 경우를 보완한다.
        if (isEmpty(itunesSearchResponse)) {
            itunesSearchResponse =
                    itunesClient.search(
                            normalizedQuery,
                            "us",
                            "music",
                            "song",
                            20
                    );
        }

        List<ItunesDto.Result> results = itunesSearchResponse.getResults() == null
                ? Collections.emptyList()
                : itunesSearchResponse.getResults();

        List<MusicDto.MusicInfo> musics = results.stream()
                .filter(result -> result.getTrackId() != null)
                .map(result -> MusicDto.MusicInfo.builder()
                        .provider("ITUNES")
                        .trackId(String.valueOf(result.getTrackId()))
                        .title(result.getTrackName())
                        .artist(result.getArtistName())
                        .artworkUrl(result.getArtworkUrl100())
                        .storeUrl(result.getTrackViewUrl())
                        .previewUrl(result.getPreviewUrl())
                        .build())
                .toList();

        return MusicDto.SearchResponse.builder()
                .musics(musics)
                .build();
    }

    private boolean isEmpty(ItunesDto.SearchResponse response) {
        return response == null
                || response.getResults() == null
                || response.getResults().isEmpty();
    }
}
