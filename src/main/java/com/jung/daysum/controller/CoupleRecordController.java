package com.jung.daysum.controller;

import com.jung.daysum.dto.CoupleRecordDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.CoupleRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "CoupleRecord")
@RestController
@RequiredArgsConstructor
@RequestMapping("/couple-records")
public class CoupleRecordController {

    private final CoupleRecordService coupleRecordService;

    @GetMapping("/{date}")
    @Operation(summary = "공동 기록 조회 [JWT O]")
    public ResponseEntity<ResponseData<CoupleRecordDto.Response>>
    findCoupleRecord(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        CoupleRecordDto.Response coupleRecordResponseDto = coupleRecordService.findCoupleRecord(date);

        return ResponseData.toResponseEntity(ResponseCode.READ_COUPLE_RECORD, coupleRecordResponseDto);
    }

    @PutMapping("/{date}")
    @Operation(summary = "공동 기록 저장 및 수정 [JWT O]")
    public ResponseEntity<ResponseData<CoupleRecordDto.Response>>
    updateCoupleRecord(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       @RequestBody CoupleRecordDto.UpdateRequest coupleRecordUpdateRequestDto
    ) {
        CoupleRecordDto.Response coupleRecordResponseDto = coupleRecordService.updateCoupleRecord(date, coupleRecordUpdateRequestDto);

        return ResponseData.toResponseEntity(ResponseCode.UPDATE_COUPLE_RECORD, coupleRecordResponseDto);
    }

}