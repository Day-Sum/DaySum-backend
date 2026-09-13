package com.jung.daysum.controller;

import com.jung.daysum.domain.enums.CalendarScope;
import com.jung.daysum.dto.CalendarDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Calendar")
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    @Operation(
            summary = "월별 캘린더 기록 날짜 조회 [JWT O]",
            description = "URI : /calendar?year=2026&month=9&scope=ME"
    )
    public ResponseEntity<ResponseData<CalendarDto.Response>> findCalendar(
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month,
            @RequestParam("scope") CalendarScope scope
    ) {

        CalendarDto.Response calendarResponseDto = calendarService.findCalendar(year, month, scope);

        return ResponseData.toResponseEntity(ResponseCode.READ_CALENDAR, calendarResponseDto);
    }
}