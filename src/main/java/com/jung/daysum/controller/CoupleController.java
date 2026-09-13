package com.jung.daysum.controller;

import com.jung.daysum.dto.CoupleDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.CoupleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Couple")
@RestController
@RequiredArgsConstructor
@RequestMapping("/couples")
public class CoupleController {

    private final CoupleService coupleService;

    @GetMapping("/status")
    @Operation(summary = "연인 연결 여부 조회 [JWT O]")
    public ResponseEntity<ResponseData<CoupleDto.StatusResponse>> findCoupleStatus() {
        CoupleDto.StatusResponse statusResponseDto = coupleService.findCoupleStatus();
        return ResponseData.toResponseEntity(ResponseCode.READ_COUPLE_STATUS, statusResponseDto);
    }

    @GetMapping("/connect-code")
    @Operation(summary = "내 연결 코드 조회 [JWT O]")
    public ResponseEntity<ResponseData<CoupleDto.ConnectCodeResponse>> findConnectCode() {
        CoupleDto.ConnectCodeResponse connectCodeResponseDto = coupleService.findConnectCode();
        return ResponseData.toResponseEntity(ResponseCode.READ_CONNECT_CODE, connectCodeResponseDto);
    }

    @GetMapping
    @Operation(summary = "커플 정보 조회 [JWT O]")
    public ResponseEntity<ResponseData<CoupleDto.Response>> findCoupleProfile() {
        CoupleDto.Response coupleResponseDto = coupleService.findCoupleProfile();
        return ResponseData.toResponseEntity(ResponseCode.READ_COUPLE, coupleResponseDto);
    }

    @PostMapping("/connect-code")
    @Operation(summary = "내 연결 코드 발급/재발급 [JWT O]")
    public ResponseEntity<ResponseData<CoupleDto.ConnectCodeResponse>> reissueConnectCode() {
        CoupleDto.ConnectCodeResponse connectCodeResponseDto = coupleService.reissueConnectCode();
        return ResponseData.toResponseEntity(ResponseCode.REISSUE_CONNECT_CODE, connectCodeResponseDto);
    }

    @PostMapping
    @Operation(summary = "연인 연결 [JWT O]")
    public ResponseEntity<ResponseData<CoupleDto.ConnectResponse>> connect(
            @RequestBody CoupleDto.ConnectRequest connectRequestDto
    ) {
        CoupleDto.ConnectResponse connectResponseDto = coupleService.connect(connectRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_COUPLE, connectResponseDto);
    }

    @PutMapping("/start-date")
    @Operation(summary = "연애 시작일 수정 [JWT O]")
    public ResponseEntity<ResponseData<CoupleDto.StartDateResponse>> updateStartDate(
            @RequestBody CoupleDto.UpdateStartDateRequest updateStartDateRequestDto
    ) {
        CoupleDto.StartDateResponse startDateResponseDto = coupleService.updateStartDate(updateStartDateRequestDto);

        return ResponseData.toResponseEntity(ResponseCode.UPDATE_COUPLE_START_DATE, startDateResponseDto);
    }

    @DeleteMapping
    @Operation(summary = "연인 연결 해제 [JWT O]")
    public ResponseEntity<ResponseData> disconnect() {
        coupleService.disconnect();
        return ResponseData.toResponseEntity(ResponseCode.DELETE_COUPLE);
    }
}