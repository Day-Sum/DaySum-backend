package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Activity;
import com.jung.daysum.domain.DailyRecord;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.HomeDto;
import com.jung.daysum.repository.ActivityRepository;
import com.jung.daysum.repository.DailyRecordRepository;
import com.jung.daysum.service.HomeService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final UserService userService;
    private final DailyRecordRepository dailyRecordRepository;
    private final ActivityRepository activityRepository;


    @Transactional(readOnly = true)
    @Override
    public HomeDto.Response findHome() {

        User loginUser = userService.findLoginUser();

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(
                        loginUser.getId(),
                        recordDate
                )
                .orElse(null);

        Activity currentActivity = activityRepository
                .findByUser_IdAndEndedAtIsNull(
                        loginUser.getId()
                )
                .orElse(null);


        HomeDto.Music music = null;

        if(dailyRecord != null &&
                dailyRecord.getMusicTrackId() != null) {

            music = HomeDto.Music.builder()
                    .provider(dailyRecord.getMusicProvider())
                    .trackId(dailyRecord.getMusicTrackId())
                    .title(dailyRecord.getMusicTitle())
                    .artist(dailyRecord.getMusicArtist())
                    .artworkUrl(dailyRecord.getMusicArtworkUrl())
                    .storeUrl(dailyRecord.getMusicStoreUrl())
                    .build();
        }


        HomeDto.CurrentActivity currentActivityDto = null;

        if(currentActivity != null) {

            currentActivityDto = HomeDto.CurrentActivity.builder()
                    .activityId(currentActivity.getId())
                    .content(currentActivity.getContent())
                    .startedAt(currentActivity.getStartedAt())
                    .build();
        }


        return HomeDto.Response.builder()
                .mood(
                        dailyRecord != null
                                ? dailyRecord.getMood()
                                : null
                )
                .photoObjectKey(
                        dailyRecord != null
                                ? dailyRecord.getPhotoObjectKey()
                                : null
                )
                .drawingObjectKey(
                        dailyRecord != null
                                ? dailyRecord.getDrawingObjectKey()
                                : null
                )
                .diaryWritten(
                        dailyRecord != null &&
                                dailyRecord.getDiaryContent() != null &&
                                !dailyRecord.getDiaryContent().isBlank()
                )
                .music(music)
                .currentActivity(currentActivityDto)
                .build();
    }
}