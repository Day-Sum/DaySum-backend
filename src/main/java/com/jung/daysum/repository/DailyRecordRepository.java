package com.jung.daysum.repository;

import com.jung.daysum.domain.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {
    Optional<DailyRecord> findByUser_IdAndRecordDate(Long userId, LocalDate recordDate);
}