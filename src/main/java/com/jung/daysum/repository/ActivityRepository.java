package com.jung.daysum.repository;

import com.jung.daysum.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByUser_IdAndEndedAtIsNull(
            Long userId
    );

    @Query("""
        SELECT a
        FROM Activity a
        WHERE a.user.id = :userId
        AND a.startedAt < :endDatetime
        AND (a.endedAt IS NULL OR a.endedAt > :startDatetime)
        ORDER BY a.startedAt ASC
        """)
    List<Activity> findAllByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("startDatetime") LocalDateTime startDatetime,
            @Param("endDatetime") LocalDateTime endDatetime
    );

}