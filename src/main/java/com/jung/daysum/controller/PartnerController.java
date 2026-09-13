package com.jung.daysum.controller;

import com.jung.daysum.dto.PartnerDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Partner")
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;


    @GetMapping("/today")
    @Operation(summary = "상대방 오늘 상태 조회 [JWT O]")
    public ResponseEntity<ResponseData<PartnerDto.TodayResponse>> findPartnerToday() {

        PartnerDto.TodayResponse partnerTodayResponseDto = partnerService.findPartnerToday();

        return ResponseData.toResponseEntity(ResponseCode.READ_PARTNER_TODAY, partnerTodayResponseDto);
    }


    @GetMapping("/daily-records/{date}")
    @Operation(summary = "상대방 날짜별 기록 조회 [JWT O]")
    public ResponseEntity<ResponseData<PartnerDto.DailyRecordResponse>>
    findPartnerDailyRecord(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        PartnerDto.DailyRecordResponse partnerDailyRecordResponseDto = partnerService.findPartnerDailyRecord(date);

        return ResponseData.toResponseEntity(ResponseCode.READ_PARTNER_DAILY_RECORD, partnerDailyRecordResponseDto);
    }


    @GetMapping("/activities")
    @Operation(
            summary = "상대방 날짜별 활동 기록 조회 [JWT O]",
            description = "URI : /partner/activities?date=yyyy-MM-dd"
    )
    public ResponseEntity<ResponseData<List<PartnerDto.ActivityResponse>>>
    findPartnerActivities(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {

        List<PartnerDto.ActivityResponse> partnerActivityResponseDtos = partnerService.findPartnerActivities(date);

        return ResponseData.toResponseEntity(ResponseCode.READ_PARTNER_ACTIVITIES, partnerActivityResponseDtos);
    }
}