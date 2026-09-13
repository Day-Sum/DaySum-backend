package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Couple;
import com.jung.daysum.domain.User;
import com.jung.daysum.domain.enums.CalendarScope;
import com.jung.daysum.dto.CalendarDto;
import com.jung.daysum.repository.DailyRecordRepository;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.service.CalendarService;
import com.jung.daysum.service.CoupleService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final UserService userService;
    private final CoupleService coupleService;
    private final DailyRecordRepository dailyRecordRepository;


    @Transactional(readOnly = true)
    @Override
    public CalendarDto.Response findCalendar(
            Integer year,
            Integer month,
            CalendarScope scope
    ) {

        User loginUser = userService.findLoginUser();

        if(year == null || month == null || scope == null) {
            throw new Exception400.DailyRecordBadRequest(
                    "캘린더 조회 조건이 올바르지 않습니다."
            );
        }

        if(month < 1 || month > 12) {
            throw new Exception400.DailyRecordBadRequest(
                    "월은 1월부터 12월까지 입력할 수 있습니다."
            );
        }

        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate startDate =
                yearMonth.atDay(1);

        LocalDate endDate =
                yearMonth.plusMonths(1).atDay(1);


        if(scope == CalendarScope.ME) {

            List<LocalDate> recordDates =
                    dailyRecordRepository.findRecordDatesByUserIdAndPeriod(
                            loginUser.getId(),
                            startDate,
                            endDate
                    );

            return CalendarDto.Response.builder()
                    .recordDates(recordDates)
                    .build();
        }


        Couple couple =
                coupleService.findCoupleWithUserId(
                        loginUser.getId()
                );

        User partnerUser =
                couple.getFirstUser().getId().equals(loginUser.getId())
                        ? couple.getSecondUser()
                        : couple.getFirstUser();

        LocalDateTime connectedAt =
                couple.getCreatedTime();

        LocalDate connectedDate =
                connectedAt.toLocalDate();


        if(!endDate.isAfter(connectedDate)) {

            return CalendarDto.Response.builder()
                    .recordDates(new ArrayList<>())
                    .build();
        }


        LocalDate searchStartDate =
                startDate.isBefore(connectedDate)
                        ? connectedDate
                        : startDate;


        if(scope == CalendarScope.PARTNER) {

            List<LocalDate> recordDates =
                    dailyRecordRepository
                            .findRecordDatesByUserIdAndPeriodFromCreatedTime(
                                    partnerUser.getId(),
                                    searchStartDate,
                                    endDate,
                                    connectedAt
                            );

            return CalendarDto.Response.builder()
                    .recordDates(recordDates)
                    .build();
        }


        List<LocalDate> myRecordDates =
                dailyRecordRepository
                        .findRecordDatesByUserIdAndPeriodFromCreatedTime(
                                loginUser.getId(),
                                searchStartDate,
                                endDate,
                                connectedAt
                        );

        List<LocalDate> partnerRecordDates =
                dailyRecordRepository
                        .findRecordDatesByUserIdAndPeriodFromCreatedTime(
                                partnerUser.getId(),
                                searchStartDate,
                                endDate,
                                connectedAt
                        );

        Set<LocalDate> coupleRecordDates =
                new TreeSet<>();

        coupleRecordDates.addAll(myRecordDates);
        coupleRecordDates.addAll(partnerRecordDates);


        return CalendarDto.Response.builder()
                .recordDates(
                        new ArrayList<>(coupleRecordDates)
                )
                .build();
    }
}