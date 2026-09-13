package com.jung.daysum.service;

import com.jung.daysum.dto.ActivityDto;

import java.time.LocalDate;
import java.util.List;

public interface ActivityService {

    ActivityDto.Response createActivity(ActivityDto.CreateRequest activityCreateRequestDto);

    void deleteCurrentActivity();

    List<ActivityDto.Response> findActivities(LocalDate date);
}