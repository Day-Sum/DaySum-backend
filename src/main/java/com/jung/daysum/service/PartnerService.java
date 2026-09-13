package com.jung.daysum.service;

import com.jung.daysum.dto.PartnerDto;

import java.time.LocalDate;
import java.util.List;

public interface PartnerService {

    PartnerDto.TodayResponse findPartnerToday();

    PartnerDto.DailyRecordResponse findPartnerDailyRecord(LocalDate date);

    List<PartnerDto.ActivityResponse> findPartnerActivities(LocalDate date);
}