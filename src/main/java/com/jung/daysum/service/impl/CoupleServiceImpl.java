package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Couple;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.CoupleDto;
import com.jung.daysum.repository.CoupleRepository;
import com.jung.daysum.repository.UserRepository;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.response.exeption.Exception404;
import com.jung.daysum.service.CoupleService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoupleServiceImpl implements CoupleService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CoupleRepository coupleRepository;

    @Transactional(readOnly = true)
    @Override
    public CoupleDto.StatusResponse findCoupleStatus() {
        User loginUser = userService.findLoginUser();

        Optional<Couple> coupleOptional =
                coupleRepository.findByUserIdWithUsers(loginUser.getId());

        return CoupleDto.StatusResponse.builder()
                .connected(coupleOptional.isPresent())
                .coupleId(coupleOptional
                        .map(Couple::getId)
                        .orElse(null))
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public CoupleDto.ConnectCodeResponse findConnectCode() {
        User loginUser = userService.findLoginUser();

        return CoupleDto.ConnectCodeResponse.builder()
                .connectCode(loginUser.getConnectCode())
                .build();
    }

    @Transactional
    @Override
    public CoupleDto.ConnectCodeResponse reissueConnectCode() {
        User loginUser = userService.findLoginUser();

        String connectCode;

        do {
            connectCode = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8)
                    .toUpperCase();
        } while(userRepository.existsByConnectCode(connectCode));

        loginUser.updateConnectCode(connectCode);

        return CoupleDto.ConnectCodeResponse.builder()
                .connectCode(connectCode)
                .build();
    }

    @Transactional
    @Override
    public CoupleDto.ConnectResponse connect(
            CoupleDto.ConnectRequest connectRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        User partnerUser = userRepository
                .findByConnectCode(connectRequestDto.getConnectCode())
                .orElseThrow(
                        () -> new Exception404.NoSuchConnectCode(
                                "connectCode = " + connectRequestDto.getConnectCode()));

        if(loginUser.getId().equals(partnerUser.getId())) {
            throw new Exception400.CoupleBadRequest(
                    "자기 자신과 연인 연결을 할 수 없습니다.");
        }

        if(coupleRepository.existsActiveCoupleByUserId(loginUser.getId())) {
            throw new Exception400.CoupleBadRequest(
                    "이미 연인과 연결되어 있는 사용자입니다.");
        }

        if(coupleRepository.existsActiveCoupleByUserId(partnerUser.getId())) {
            throw new Exception400.CoupleBadRequest(
                    "상대방은 이미 다른 사용자와 연결되어 있습니다.");
        }

        if(connectRequestDto.getConnectCode() == null ||
                connectRequestDto.getConnectCode().isBlank()) {
            throw new Exception400.ConnectCodeBadRequest(
                    "연결 코드가 입력되지 않았습니다.");
        }

        if(connectRequestDto.getRelationshipStartedOn() == null) {
            throw new Exception400.CoupleBadRequest(
                    "연애 시작일이 입력되지 않았습니다.");
        }

        Couple couple = Couple.CoupleSaveBuilder()
                .firstUser(loginUser)
                .secondUser(partnerUser)
                .startedAt(connectRequestDto.getRelationshipStartedOn())
                .build();
        coupleRepository.save(couple);

        return new CoupleDto.ConnectResponse(couple, partnerUser);
    }

    @Transactional(readOnly = true)
    @Override
    public CoupleDto.Response findCoupleProfile() {
        User loginUser = userService.findLoginUser();
        Couple couple = findCoupleWithUserId(loginUser.getId());

        User partnerUser = couple.getFirstUser().getId().equals(loginUser.getId())
                        ? couple.getSecondUser()
                        : couple.getFirstUser();

        Long dayCount = ChronoUnit.DAYS.between(couple.getStartedAt(), LocalDate.now()) + 1;

        return new CoupleDto.Response(
                couple,
                partnerUser,
                dayCount
        );
    }

    @Transactional
    @Override
    public CoupleDto.StartDateResponse updateStartDate(
            CoupleDto.UpdateStartDateRequest updateStartDateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(updateStartDateRequestDto.getRelationshipStartedOn() == null) {
            throw new Exception400.CoupleBadRequest(
                    "연애 시작일이 입력되지 않았습니다.");
        }

        Couple couple = findCoupleWithUserId(loginUser.getId());

        couple.updateStartedAt(updateStartDateRequestDto.getRelationshipStartedOn());

        Long dayCount = ChronoUnit.DAYS.between(couple.getStartedAt(), LocalDate.now()) + 1;

        return CoupleDto.StartDateResponse.builder()
                .relationshipStartedOn(couple.getStartedAt())
                .dayCount(dayCount)
                .build();
    }

    @Transactional
    @Override
    public void disconnect() {
        User loginUser = userService.findLoginUser();
        Couple couple = findCoupleWithUserId(loginUser.getId());

        couple.disconnect();
    }

    // ========== 유틸성 메소드 ========== //

    @Transactional(readOnly = true)
    @Override
    public Couple findCoupleWithUserId(Long userId) {
        return coupleRepository.findByUserIdWithUsers(userId).orElseThrow(
                () -> new Exception404.NoSuchCouple(
                        String.format("userId = %d", userId)));
    }

}