package com.jung.daysum.controller;

import com.jung.daysum.dto.MusicDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Music")
@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;


    @GetMapping("/search")
    @Operation(summary = "음악 검색 [JWT O]")
    public ResponseEntity<ResponseData<MusicDto.SearchResponse>> searchMusic(@RequestParam(value = "query") String query
    ) {
        MusicDto.SearchResponse musicSearchResponseDto = musicService.searchMusic(query);

        return ResponseData.toResponseEntity(ResponseCode.READ_MUSIC_SEARCH, musicSearchResponseDto);
    }
}