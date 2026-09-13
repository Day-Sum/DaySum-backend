package com.jung.daysum.repository;

import com.jung.daysum.domain.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

    Optional<DailyRecord> findByUser_IdAndRecordDate(
            Long userId,
            LocalDate recordDate
    );

    @Query("""
        SELECT DISTINCT d.recordDate
        FROM DailyRecord d
        WHERE d.user.id = :userId
        AND d.recordDate >= :startDate
        AND d.recordDate < :endDate
        ORDER BY d.recordDate ASC
        """)
    List<LocalDate> findRecordDatesByUserIdAndPeriod(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT DISTINCT d.recordDate
        FROM DailyRecord d
        WHERE d.user.id = :userId
        AND d.recordDate >= :startDate
        AND d.recordDate < :endDate
        AND d.createdTime >= :createdTime
        ORDER BY d.recordDate ASC
        """)
    List<LocalDate> findRecordDatesByUserIdAndPeriodFromCreatedTime(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("createdTime") LocalDateTime createdTime
    );
}