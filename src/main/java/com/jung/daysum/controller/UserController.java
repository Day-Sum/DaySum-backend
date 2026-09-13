package com.jung.daysum.controller;

import com.jung.daysum.dto.UserDto;
import com.jung.daysum.response.ResponseCode;
import com.jung.daysum.response.ResponseData;
import com.jung.daysum.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "회원 정보 조회 [JWT O]")
    public ResponseEntity<ResponseData<UserDto.Response>> findUserProfile() {
        UserDto.Response userResponseDto = userService.findUserProfile();
        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userResponseDto);
    }
}
