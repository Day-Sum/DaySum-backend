package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Couple;
import com.jung.daysum.domain.DailyRecord;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.DailyRecordDto;
import com.jung.daysum.repository.DailyRecordRepository;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.response.exeption.Exception404;
import com.jung.daysum.service.CoupleService;
import com.jung.daysum.service.DailyRecordService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyRecordServiceImpl implements DailyRecordService {

    private final UserService userService;
    private final CoupleService coupleService;
    private final DailyRecordRepository dailyRecordRepository;


    @Transactional
    @Override
    public DailyRecordDto.MoodResponse updateTodayMood(
            DailyRecordDto.MoodUpdateRequest moodUpdateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(moodUpdateRequestDto.getMood() == null ||
                moodUpdateRequestDto.getMood().isBlank()) {
            throw new Exception400.DailyRecordBadRequest(
                    "기분이 입력되지 않았습니다.");
        }

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(loginUser.getId(), recordDate)
                .orElse(null);

        if(dailyRecord == null) {
            dailyRecord = DailyRecord.DailyRecordSaveBuilder()
                    .user(loginUser)
                    .recordDate(recordDate)
                    .build();

            dailyRecord.updateMood(moodUpdateRequestDto.getMood());
            dailyRecordRepository.save(dailyRecord);
        }
        else {
            dailyRecord.updateMood(moodUpdateRequestDto.getMood());
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.MoodResponse.builder()
                .mood(dailyRecord.getMood())
                .version(dailyRecord.getVersion())
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public DailyRecordDto.Response findDailyRecordByDate(
            LocalDate recordDate
    ) {
        User loginUser = userService.findLoginUser();

        DailyRecord dailyRecord =
                findDailyRecord(loginUser.getId(), recordDate);

        DailyRecordDto.Response dailyRecordResponseDto =
                new DailyRecordDto.Response(dailyRecord);

        return dailyRecordResponseDto;
    }


    @Transactional
    @Override
    public DailyRecordDto.DiaryResponse updateDiary(
            LocalDate recordDate,
            DailyRecordDto.DiaryUpdateRequest diaryUpdateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(diaryUpdateRequestDto.getDiaryContent() == null ||
                diaryUpdateRequestDto.getDiaryContent().isBlank()) {
            throw new Exception400.DailyRecordBadRequest(
                    "일기 내용이 입력되지 않았습니다.");
        }

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(loginUser.getId(), recordDate)
                .orElse(null);

        if(dailyRecord == null) {
            dailyRecord = DailyRecord.DailyRecordSaveBuilder()
                    .user(loginUser)
                    .recordDate(recordDate)
                    .build();

            dailyRecord.updateDiary(diaryUpdateRequestDto.getDiaryContent());
            dailyRecordRepository.save(dailyRecord);
        }
        else {
            dailyRecord.updateDiary(diaryUpdateRequestDto.getDiaryContent());
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.DiaryResponse.builder()
                .diaryContent(dailyRecord.getDiaryContent())
                .version(dailyRecord.getVersion())
                .build();
    }


    @Transactional
    @Override
    public void deleteDiary(LocalDate recordDate) {
        User loginUser = userService.findLoginUser();

        DailyRecord dailyRecord =
                findDailyRecord(loginUser.getId(), recordDate);

        dailyRecord.deleteDiary();
    }


    @Transactional
    @Override
    public DailyRecordDto.DiaryShareResponse updateDiaryShare(
            LocalDate recordDate,
            DailyRecordDto.DiaryShareRequest diaryShareRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(diaryShareRequestDto.getShared() == null) {
            throw new Exception400.DailyRecordBadRequest(
                    "일기 공유 여부가 입력되지 않았습니다.");
        }

        DailyRecord dailyRecord =
                findDailyRecord(loginUser.getId(), recordDate);

        if(diaryShareRequestDto.getShared() == true) {
            if(dailyRecord.getDiaryContent() == null ||
                    dailyRecord.getDiaryContent().isBlank()) {
                throw new Exception400.DailyRecordBadRequest(
                        "공유할 일기가 존재하지 않습니다.");
            }

            Couple couple =
                    coupleService.findCoupleWithUserId(loginUser.getId());

            dailyRecord.updateDiarySharedCoupleId(couple.getId());
        }
        else {
            dailyRecord.updateDiarySharedCoupleId(null);
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.DiaryShareResponse.builder()
                .shared(dailyRecord.getDiarySharedCoupleId() != null)
                .version(dailyRecord.getVersion())
                .build();
    }


    // ========== 유틸성 메소드 ========== //

    @Transactional(readOnly = true)
    @Override
    public DailyRecord findDailyRecord(
            Long userId,
            LocalDate recordDate
    ) {
        return dailyRecordRepository
                .findByUser_IdAndRecordDate(userId, recordDate)
                .orElseThrow(
                        () -> new Exception404.NoSuchDailyRecord(
                                String.format(
                                        "userId = %d, recordDate = %s",
                                        userId,
                                        recordDate
                                )
                        )
                );
    }
}