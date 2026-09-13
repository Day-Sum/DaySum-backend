package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Activity;
import com.jung.daysum.domain.Couple;
import com.jung.daysum.domain.DailyRecord;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.PartnerDto;
import com.jung.daysum.repository.ActivityRepository;
import com.jung.daysum.repository.DailyRecordRepository;
import com.jung.daysum.response.exeption.Exception404;
import com.jung.daysum.service.CoupleService;
import com.jung.daysum.service.DailyRecordService;
import com.jung.daysum.service.PartnerService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final UserService userService;
    private final CoupleService coupleService;
    private final DailyRecordService dailyRecordService;
    private final DailyRecordRepository dailyRecordRepository;
    private final ActivityRepository activityRepository;


    @Transactional(readOnly = true)
    @Override
    public PartnerDto.TodayResponse findPartnerToday() {

        User loginUser = userService.findLoginUser();

        Couple couple =
                coupleService.findCoupleWithUserId(
                        loginUser.getId()
                );

        User partnerUser =
                couple.getFirstUser().getId().equals(loginUser.getId())
                        ? couple.getSecondUser()
                        : couple.getFirstUser();

        LocalDate recordDate = LocalDate.now();
        LocalDateTime connectedAt = couple.getCreatedTime();

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(
                        partnerUser.getId(),
                        recordDate
                )
                .orElse(null);

        Activity currentActivity = activityRepository
                .findByUser_IdAndEndedAtIsNull(
                        partnerUser.getId()
                )
                .orElse(null);


        if(dailyRecord != null &&
                dailyRecord.getCreatedTime().isBefore(connectedAt)) {

            dailyRecord = null;
        }

        if(currentActivity != null &&
                currentActivity.getStartedAt().isBefore(connectedAt)) {

            currentActivity = null;
        }


        boolean diaryShared =
                dailyRecord != null &&
                        dailyRecord.getDiarySharedCoupleId() != null &&
                        dailyRecord.getDiarySharedCoupleId().equals(couple.getId());


        PartnerDto.Music music = null;

        if(dailyRecord != null &&
                dailyRecord.getMusicTrackId() != null) {

            music = PartnerDto.Music.builder()
                    .provider(dailyRecord.getMusicProvider())
                    .trackId(dailyRecord.getMusicTrackId())
                    .title(dailyRecord.getMusicTitle())
                    .artist(dailyRecord.getMusicArtist())
                    .artworkUrl(dailyRecord.getMusicArtworkUrl())
                    .storeUrl(dailyRecord.getMusicStoreUrl())
                    .build();
        }


        PartnerDto.CurrentActivity currentActivityDto = null;

        if(currentActivity != null) {

            currentActivityDto =
                    PartnerDto.CurrentActivity.builder()
                            .activityId(currentActivity.getId())
                            .content(currentActivity.getContent())
                            .startedAt(currentActivity.getStartedAt())
                            .build();
        }


        return PartnerDto.TodayResponse.builder()
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
                .diaryShared(diaryShared)
                .music(music)
                .currentActivity(currentActivityDto)
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public PartnerDto.DailyRecordResponse findPartnerDailyRecord(
            LocalDate date
    ) {

        User loginUser = userService.findLoginUser();

        Couple couple =
                coupleService.findCoupleWithUserId(
                        loginUser.getId()
                );

        User partnerUser =
                couple.getFirstUser().getId().equals(loginUser.getId())
                        ? couple.getSecondUser()
                        : couple.getFirstUser();

        LocalDateTime connectedAt = couple.getCreatedTime();


        if(date.isBefore(connectedAt.toLocalDate())) {

            throw new Exception404.NoSuchDailyRecord(
                    String.format(
                            "partnerUserId = %d, recordDate = %s",
                            partnerUser.getId(),
                            date
                    )
            );
        }


        DailyRecord dailyRecord =
                dailyRecordService.findDailyRecord(
                        partnerUser.getId(),
                        date
                );


        if(dailyRecord.getCreatedTime().isBefore(connectedAt)) {

            throw new Exception404.NoSuchDailyRecord(
                    String.format(
                            "partnerUserId = %d, recordDate = %s",
                            partnerUser.getId(),
                            date
                    )
            );
        }


        boolean diaryShared =
                dailyRecord.getDiarySharedCoupleId() != null &&
                        dailyRecord.getDiarySharedCoupleId().equals(couple.getId());


        PartnerDto.Music music = null;

        if(dailyRecord.getMusicTrackId() != null) {

            music = PartnerDto.Music.builder()
                    .provider(dailyRecord.getMusicProvider())
                    .trackId(dailyRecord.getMusicTrackId())
                    .title(dailyRecord.getMusicTitle())
                    .artist(dailyRecord.getMusicArtist())
                    .artworkUrl(dailyRecord.getMusicArtworkUrl())
                    .storeUrl(dailyRecord.getMusicStoreUrl())
                    .build();
        }


        return PartnerDto.DailyRecordResponse.builder()
                .dailyRecordId(dailyRecord.getId())
                .recordDate(dailyRecord.getRecordDate())
                .mood(dailyRecord.getMood())
                .photoObjectKey(dailyRecord.getPhotoObjectKey())
                .drawingObjectKey(dailyRecord.getDrawingObjectKey())
                .diaryContent(
                        diaryShared
                                ? dailyRecord.getDiaryContent()
                                : null
                )
                .diaryShared(diaryShared)
                .music(music)
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public List<PartnerDto.ActivityResponse> findPartnerActivities(
            LocalDate date
    ) {

        User loginUser = userService.findLoginUser();

        Couple couple =
                coupleService.findCoupleWithUserId(
                        loginUser.getId()
                );

        User partnerUser =
                couple.getFirstUser().getId().equals(loginUser.getId())
                        ? couple.getSecondUser()
                        : couple.getFirstUser();

        LocalDateTime connectedAt = couple.getCreatedTime();


        if(date.isBefore(connectedAt.toLocalDate())) {
            return new ArrayList<>();
        }


        LocalDateTime startDatetime =
                date.atStartOfDay();

        LocalDateTime endDatetime =
                date.plusDays(1).atStartOfDay();

        List<Activity> activities =
                activityRepository.findAllByUserIdAndDate(
                        partnerUser.getId(),
                        startDatetime,
                        endDatetime
                );

        List<PartnerDto.ActivityResponse> activityResponseDtos =
                new ArrayList<>();

        for(Activity activity : activities) {

            if(activity.getStartedAt().isBefore(connectedAt)) {
                continue;
            }

            activityResponseDtos.add(
                    PartnerDto.ActivityResponse.builder()
                            .activityId(activity.getId())
                            .content(activity.getContent())
                            .startedAt(activity.getStartedAt())
                            .endedAt(activity.getEndedAt())
                            .build()
            );
        }

        return activityResponseDtos;
    }
}