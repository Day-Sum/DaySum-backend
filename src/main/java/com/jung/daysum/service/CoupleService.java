package com.jung.daysum.service;

import com.jung.daysum.domain.Couple;
import com.jung.daysum.dto.CoupleDto;

public interface CoupleService {

    CoupleDto.StatusResponse findCoupleStatus();

    CoupleDto.ConnectCodeResponse findConnectCode();

    CoupleDto.ConnectCodeResponse reissueConnectCode();

    CoupleDto.ConnectResponse connect(CoupleDto.ConnectRequest connectRequestDto);

    CoupleDto.Response findCoupleProfile();

    CoupleDto.StartDateResponse updateStartDate(
            CoupleDto.UpdateStartDateRequest updateStartDateRequestDto
    );

    void disconnect();

    // ========== 유틸성 메소드 ========== //

    Couple findCoupleWithUserId(Long userId);

}