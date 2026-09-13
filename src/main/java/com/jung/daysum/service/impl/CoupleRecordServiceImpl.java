package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Couple;
import com.jung.daysum.domain.CoupleRecord;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.CoupleRecordDto;
import com.jung.daysum.repository.CoupleRecordRepository;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.response.exeption.Exception404;
import com.jung.daysum.response.exeption.Exception409;
import com.jung.daysum.service.CoupleRecordService;
import com.jung.daysum.service.CoupleService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CoupleRecordServiceImpl implements CoupleRecordService {

    private final UserService userService;
    private final CoupleService coupleService;
    private final CoupleRecordRepository coupleRecordRepository;


    @Transactional(readOnly = true)
    @Override
    public CoupleRecordDto.Response findCoupleRecord(
            LocalDate date
    ) {

        User loginUser = userService.findLoginUser();

        Couple couple =
                coupleService.findCoupleWithUserId(
                        loginUser.getId()
                );


        if(date.isBefore(
                couple.getCreatedTime().toLocalDate()
        )) {

            throw new Exception404.NoSuchCoupleRecord(
                    String.format(
                            "coupleId = %d, recordDate = %s",
                            couple.getId(),
                            date
                    )
            );
        }


        CoupleRecord coupleRecord =
                coupleRecordRepository
                        .findByCouple_IdAndRecordDate(
                                couple.getId(),
                                date
                        )
                        .orElseThrow(
                                () -> new Exception404.NoSuchCoupleRecord(
                                        String.format(
                                                "coupleId = %d, recordDate = %s",
                                                couple.getId(),
                                                date
                                        )
                                )
                        );


        return new CoupleRecordDto.Response(
                coupleRecord
        );
    }


    @Transactional
    @Override
    public CoupleRecordDto.Response updateCoupleRecord(
            LocalDate date,
            CoupleRecordDto.UpdateRequest coupleRecordUpdateRequestDto
    ) {

        User loginUser = userService.findLoginUser();


        Couple couple =
                coupleService.findCoupleWithUserIdForUpdate(
                        loginUser.getId()
                );


        if(date.isBefore(
                couple.getCreatedTime().toLocalDate()
        )) {

            throw new Exception400.CoupleBadRequest(
                    "연인 연결 이전 날짜의 공동 기록은 작성할 수 없습니다."
            );
        }


        CoupleRecord coupleRecord =
                coupleRecordRepository
                        .findByCouple_IdAndRecordDate(
                                couple.getId(),
                                date
                        )
                        .orElse(null);


        if(coupleRecord == null) {

            if(coupleRecordUpdateRequestDto.getVersion() != null) {

                throw new Exception409.ConflictCoupleRecord(
                        "공동 기록 버전이 일치하지 않습니다."
                );
            }


            coupleRecord =
                    CoupleRecord.CoupleRecordSaveBuilder()
                            .couple(couple)
                            .recordDate(date)
                            .content(
                                    coupleRecordUpdateRequestDto
                                            .getContent()
                            )
                            .updatedByUserId(
                                    loginUser.getId()
                            )
                            .build();


            coupleRecordRepository
                    .saveAndFlush(coupleRecord);


            return new CoupleRecordDto.Response(
                    coupleRecord
            );
        }


        if(coupleRecordUpdateRequestDto.getVersion() == null ||
                !coupleRecord.getVersion().equals(
                        coupleRecordUpdateRequestDto.getVersion()
                )) {

            throw new Exception409.ConflictCoupleRecord(
                    "공동 기록 버전이 일치하지 않습니다."
            );
        }


        coupleRecord.updateRecord(
                coupleRecordUpdateRequestDto.getContent(),
                loginUser.getId()
        );


        try {

            coupleRecordRepository.flush();

        } catch(OptimisticLockingFailureException ex) {

            throw new Exception409.ConflictCoupleRecord(
                    "공동 기록이 다른 사용자에 의해 수정되었습니다."
            );
        }


        return new CoupleRecordDto.Response(
                coupleRecord
        );
    }
}