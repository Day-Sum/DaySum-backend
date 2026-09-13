package com.jung.daysum.service.impl;

import com.jung.daysum.client.ItunesClient;
import com.jung.daysum.dto.ItunesDto;
import com.jung.daysum.dto.MusicDto;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final ItunesClient itunesClient;

    @Override
    public MusicDto.SearchResponse searchMusic(String query) {

        if(query == null || query.isBlank()) {
            throw new Exception400.MusicBadRequest(
                    "검색어가 입력되지 않았습니다."
            );
        }

        ItunesDto.SearchResponse itunesSearchResponse =
                itunesClient.search(
                        query,
                        "KR",
                        "music",
                        "song",
                        20
                );

        List<MusicDto.MusicInfo> musics =
                new ArrayList<>();

        for(ItunesDto.Result result :
                itunesSearchResponse.getResults()) {

            MusicDto.MusicInfo music =
                    MusicDto.MusicInfo.builder()
                            .provider("ITUNES")
                            .trackId(
                                    String.valueOf(
                                            result.getTrackId()
                                    )
                            )
                            .title(result.getTrackName())
                            .artist(result.getArtistName())
                            .artworkUrl(
                                    result.getArtworkUrl100()
                            )
                            .storeUrl(
                                    result.getTrackViewUrl()
                            )
                            .build();

            musics.add(music);
        }

        return MusicDto.SearchResponse.builder()
                .musics(musics)
                .build();
    }
}