package com.jung.daysum.controller;

import com.jung.daysum.dto.DailyRecordDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.DailyRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Tag(name = "DailyRecord")
@RestController
@RequiredArgsConstructor
@RequestMapping("/daily-records")
public class DailyRecordController {

    private final DailyRecordService dailyRecordService;

    @PutMapping("/today/mood")
    @Operation(summary = "오늘 기분 저장/수정 [JWT O]")
    public ResponseEntity<ResponseData<DailyRecordDto.MoodResponse>> updateTodayMood(
            @RequestBody DailyRecordDto.MoodUpdateRequest moodUpdateRequestDto
    ) {
        DailyRecordDto.MoodResponse moodResponseDto = dailyRecordService.updateTodayMood(moodUpdateRequestDto);

        return ResponseData.toResponseEntity(ResponseCode.UPDATE_DAILY_RECORD_MOOD, moodResponseDto);
    }

    @GetMapping("/{date}")
    @Operation(
            summary = "날짜별 일일 기록 조회 [JWT O]",
            description = "URI : /daily-records/{date} / date 형식 : yyyy-MM-dd"
    )
    public ResponseEntity<ResponseData<DailyRecordDto.Response>> findDailyRecordByDate(
            @PathVariable(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate recordDate
    ) {
        DailyRecordDto.Response dailyRecordResponseDto = dailyRecordService.findDailyRecordByDate(recordDate);

        return ResponseData.toResponseEntity(ResponseCode.READ_DAILY_RECORD, dailyRecordResponseDto);
    }

    @PutMapping("/{date}/diary")
    @Operation(
            summary = "날짜별 일기 저장/수정 [JWT O]",
            description = "URI : /daily-records/{date}/diary / date 형식 : yyyy-MM-dd"
    )
    public ResponseEntity<ResponseData<DailyRecordDto.DiaryResponse>> updateDiary(
            @PathVariable(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate recordDate,
            @RequestBody DailyRecordDto.DiaryUpdateRequest diaryUpdateRequestDto
    ) {
        DailyRecordDto.DiaryResponse diaryResponseDto = dailyRecordService.updateDiary(recordDate, diaryUpdateRequestDto);

        return ResponseData.toResponseEntity(ResponseCode.UPDATE_DAILY_RECORD_DIARY, diaryResponseDto);
    }


    @DeleteMapping("/{date}/diary")
    @Operation(
            summary = "날짜별 일기 삭제 [JWT O]",
            description = "URI : /daily-records/{date}/diary / date 형식 : yyyy-MM-dd"
    )
    public ResponseEntity<ResponseData> deleteDiary(
            @PathVariable(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate recordDate
    ) {
        dailyRecordService.deleteDiary(recordDate);

        return ResponseData.toResponseEntity(ResponseCode.DELETE_DAILY_RECORD_DIARY);
    }


    @PutMapping("/{date}/diary/share")
    @Operation(
            summary = "날짜별 일기 공유 상태 수정 [JWT O]",
            description = "URI : /daily-records/{date}/diary/share / shared : true(공유) or false(공유 해제)"
    )
    public ResponseEntity<ResponseData<DailyRecordDto.DiaryShareResponse>> updateDiaryShare(
            @PathVariable(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate recordDate,
            @RequestBody DailyRecordDto.DiaryShareRequest diaryShareRequestDto
    ) {
        DailyRecordDto.DiaryShareResponse diaryShareResponseDto = dailyRecordService.updateDiaryShare(recordDate, diaryShareRequestDto);

        return ResponseData.toResponseEntity(ResponseCode.UPDATE_DAILY_RECORD_DIARY_SHARE, diaryShareResponseDto);
    }

    @PutMapping(
            value = "/today/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "오늘 사진 저장/수정 [JWT O]")
    public ResponseEntity<ResponseData<DailyRecordDto.PhotoResponse>> updateTodayPhoto(
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        DailyRecordDto.PhotoResponse photoResponseDto = dailyRecordService.updateTodayPhoto(imageFile);

        return ResponseData.toResponseEntity(ResponseCode.UPDATE_DAILY_RECORD_PHOTO, photoResponseDto);
    }

    @DeleteMapping("/today/photo")
    @Operation(summary = "오늘 사진 삭제 [JWT O]")
    public ResponseEntity<ResponseData> deleteTodayPhoto() {

        dailyRecordService.deleteTodayPhoto();

        return ResponseData.toResponseEntity(ResponseCode.DELETE_DAILY_RECORD_PHOTO);
    }

    @PutMapping(
            value = "/today/drawing",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "오늘 그림 저장/수정 [JWT O]")
    public ResponseEntity<ResponseData<DailyRecordDto.DrawingResponse>> updateTodayDrawing(
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        DailyRecordDto.DrawingResponse drawingResponseDto = dailyRecordService.updateTodayDrawing(imageFile);

        return ResponseData.toResponseEntity(ResponseCode.UPDATE_DAILY_RECORD_DRAWING, drawingResponseDto);
    }

    @DeleteMapping("/today/drawing")
    @Operation(summary = "오늘 그림 삭제 [JWT O]")
    public ResponseEntity<ResponseData> deleteTodayDrawing() {

        dailyRecordService.deleteTodayDrawing();

        return ResponseData.toResponseEntity(ResponseCode.DELETE_DAILY_RECORD_DRAWING);
    }

}