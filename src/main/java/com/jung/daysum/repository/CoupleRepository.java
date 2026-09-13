package com.jung.daysum.repository;

import com.jung.daysum.domain.Couple;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {

    @Query("""
            SELECT c
            FROM Couple c
            JOIN FETCH c.firstUser
            JOIN FETCH c.secondUser
            WHERE (c.firstUser.id = :userId OR c.secondUser.id = :userId)
            AND c.endedAt IS NULL
            """)
    Optional<Couple> findByUserIdWithUsers(
            @Param("userId") Long userId
    );


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT c
            FROM Couple c
            WHERE (c.firstUser.id = :userId OR c.secondUser.id = :userId)
            AND c.endedAt IS NULL
            """)
    Optional<Couple> findByUserIdForUpdate(
            @Param("userId") Long userId
    );


    @Query("""
            SELECT COUNT(c) > 0
            FROM Couple c
            WHERE (c.firstUser.id = :userId OR c.secondUser.id = :userId)
            AND c.endedAt IS NULL
            """)
    boolean existsActiveCoupleByUserId(
            @Param("userId") Long userId
    );
}