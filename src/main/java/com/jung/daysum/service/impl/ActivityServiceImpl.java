package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Activity;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.ActivityDto;
import com.jung.daysum.repository.ActivityRepository;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.response.exeption.Exception404;
import com.jung.daysum.service.ActivityService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final UserService userService;
    private final ActivityRepository activityRepository;


    @Transactional
    @Override
    public ActivityDto.Response createActivity(
            ActivityDto.CreateRequest activityCreateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(activityCreateRequestDto.getContent() == null ||
                activityCreateRequestDto.getContent().isBlank()) {
            throw new Exception400.ActivityBadRequest(
                    "활동 내용이 입력되지 않았습니다."
            );
        }

        Activity currentActivity = activityRepository
                .findByUser_IdAndEndedAtIsNull(loginUser.getId())
                .orElse(null);

        if(currentActivity != null) {
            currentActivity.end();
        }

        Activity activity = Activity.ActivitySaveBuilder()
                .user(loginUser)
                .content(activityCreateRequestDto.getContent())
                .startedAt(LocalDateTime.now())
                .build();

        activityRepository.save(activity);

        return new ActivityDto.Response(activity);
    }


    @Transactional
    @Override
    public void deleteCurrentActivity() {
        User loginUser = userService.findLoginUser();

        Activity currentActivity = activityRepository
                .findByUser_IdAndEndedAtIsNull(loginUser.getId())
                .orElseThrow(
                        () -> new Exception404.NoSuchCurrentActivity(
                                String.format(
                                        "userId = %d",
                                        loginUser.getId()
                                )
                        )
                );

        currentActivity.end();
    }


    @Transactional(readOnly = true)
    @Override
    public List<ActivityDto.Response> findActivities(
            LocalDate date
    ) {
        User loginUser = userService.findLoginUser();

        if(date == null) {
            throw new Exception400.ActivityBadRequest(
                    "조회 날짜가 입력되지 않았습니다."
            );
        }

        LocalDateTime startDatetime =
                date.atStartOfDay();

        LocalDateTime endDatetime =
                date.plusDays(1).atStartOfDay();

        List<Activity> activities =
                activityRepository.findAllByUserIdAndDate(
                        loginUser.getId(),
                        startDatetime,
                        endDatetime
                );

        List<ActivityDto.Response> activityResponseDtos =
                new ArrayList<>();

        for(Activity activity : activities) {
            activityResponseDtos.add(
                    new ActivityDto.Response(activity)
            );
        }

        return activityResponseDtos;
    }
}