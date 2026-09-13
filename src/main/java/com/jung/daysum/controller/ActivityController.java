package com.jung.daysum.controller;

import com.jung.daysum.dto.ActivityDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Activity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;


    @PostMapping
    @Operation(summary = "현재 활동 시작/변경 [JWT O]")
    public ResponseEntity<ResponseData<ActivityDto.Response>> createActivity(
            @RequestBody ActivityDto.CreateRequest activityCreateRequestDto
    ) {
        ActivityDto.Response activityResponseDto = activityService.createActivity(activityCreateRequestDto);

        return ResponseData.toResponseEntity(ResponseCode.CREATED_ACTIVITY, activityResponseDto);
    }


    @DeleteMapping("/current")
    @Operation(summary = "현재 활동 종료 [JWT O]")
    public ResponseEntity<ResponseData> deleteCurrentActivity() {

        activityService.deleteCurrentActivity();

        return ResponseData.toResponseEntity(ResponseCode.DELETE_CURRENT_ACTIVITY);
    }

    @GetMapping
    @Operation(
            summary = "날짜별 활동 기록 조회 [JWT O]",
            description = "URI : /activities?date=yyyy-MM-dd"
    )
    public ResponseEntity<ResponseData<List<ActivityDto.Response>>> findActivities(
            @RequestParam(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        List<ActivityDto.Response> activityResponseDtos = activityService.findActivities(date);

        return ResponseData.toResponseEntity(ResponseCode.READ_ACTIVITIES, activityResponseDtos);
    }
}