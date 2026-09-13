package com.jung.daysum.service;

import com.jung.daysum.dto.MusicDto;

public interface MusicService {

    MusicDto.SearchResponse searchMusic(String query);
}