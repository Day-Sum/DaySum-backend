package com.jung.daysum.service;

import com.jung.daysum.domain.DailyRecord;
import com.jung.daysum.dto.DailyRecordDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public interface DailyRecordService {

    DailyRecordDto.MoodResponse updateTodayMood(
            DailyRecordDto.MoodUpdateRequest moodUpdateRequestDto
    );

    DailyRecordDto.PhotoResponse updateTodayPhoto(
            MultipartFile imageFile
    ) throws IOException;

    void deleteTodayPhoto();

    DailyRecordDto.DrawingResponse updateTodayDrawing(
            MultipartFile imageFile
    ) throws IOException;

    void deleteTodayDrawing();

    DailyRecordDto.Response findDailyRecordByDate(
            LocalDate recordDate
    );

    DailyRecordDto.DiaryResponse updateDiary(
            LocalDate recordDate,
            DailyRecordDto.DiaryUpdateRequest diaryUpdateRequestDto
    );

    void deleteDiary(
            LocalDate recordDate
    );

    DailyRecordDto.DiaryShareResponse updateDiaryShare(
            LocalDate recordDate,
            DailyRecordDto.DiaryShareRequest diaryShareRequestDto
    );


    // ========== 유틸성 메소드 ========== //

    DailyRecord findDailyRecord(
            Long userId,
            LocalDate recordDate
    );
}