package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Activity;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.ActivityDto;
import com.jung.daysum.repository.ActivityRepository;
import com.jung.daysum.response.exeption.Exception400;
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

    private static final int MAX_ACTIVITY_LENGTH = 30;

    private final UserService userService;
    private final ActivityRepository activityRepository;


    @Transactional
    @Override
    public ActivityDto.SaveResponse updateCurrentActivity(
            ActivityDto.UpdateRequest activityUpdateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        String activityContent = normalizeActivity(
                activityUpdateRequestDto.getActivity()
        );

        Activity currentActivity = activityRepository
                .findByUser_IdAndEndedAtIsNull(loginUser.getId())
                .orElse(null);

        // 현재 진행 중인 활동이 없으면 새로 생성
        if(currentActivity == null) {

            Activity activity = Activity.ActivitySaveBuilder()
                    .user(loginUser)
                    .content(activityContent)
                    .startedAt(LocalDateTime.now())
                    .build();

            activityRepository.save(activity);

            return new ActivityDto.SaveResponse(activity);
        }

        // 내용이 같으면 아무것도 하지 않음
        if(currentActivity.getContent().equals(activityContent)) {
            return new ActivityDto.SaveResponse(currentActivity);
        }

        // 현재 활동의 같은 row만 수정
        currentActivity.updateActivity(activityContent);

        return new ActivityDto.SaveResponse(currentActivity);
    }


    @Transactional
    @Override
    public void deleteCurrentActivity() {
        User loginUser = userService.findLoginUser();

        // DELETE는 현재 활동이 이미 없어도 성공하도록 idempotent하게 처리한다.
        activityRepository
                .findByUser_IdAndEndedAtIsNull(loginUser.getId())
                .ifPresent(activity -> activity.end(LocalDateTime.now()));
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


    private String normalizeActivity(String activity) {

        if(activity == null || activity.isBlank()) {
            throw new Exception400.ActivityBadRequest(
                    "활동 내용이 입력되지 않았습니다."
            );
        }

        String normalizedActivity = activity.trim();

        if(normalizedActivity.length() > MAX_ACTIVITY_LENGTH) {
            throw new Exception400.ActivityBadRequest(
                    String.format(
                            "활동 내용은 %d자 이하여야 합니다.",
                            MAX_ACTIVITY_LENGTH
                    )
            );
        }

        return normalizedActivity;
    }
}
