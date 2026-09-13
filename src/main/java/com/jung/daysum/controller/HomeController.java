package com.jung.daysum.controller;

import com.jung.daysum.dto.HomeDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Home")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    @Operation(summary = "홈 정보 조회 [JWT O]")
    public ResponseEntity<ResponseData<HomeDto.Response>> findHome() {

        HomeDto.Response homeResponseDto =
                homeService.findHome();

        return ResponseData.toResponseEntity(ResponseCode.READ_HOME, homeResponseDto);
    }
}