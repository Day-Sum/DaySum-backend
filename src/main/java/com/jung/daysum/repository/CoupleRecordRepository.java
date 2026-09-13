package com.jung.daysum.repository;

import com.jung.daysum.domain.CoupleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CoupleRecordRepository extends JpaRepository<CoupleRecord, Long> {

    Optional<CoupleRecord> findByCouple_IdAndRecordDate(Long coupleId, LocalDate recordDate);

}