package com.jung.daysum.domain;

import com.jung.daysum.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor

@Table(name = "daily_record")
@Entity
public class DailyRecord extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "mood")
    private String mood;

    @Column(name = "diary_content", columnDefinition = "TEXT")
    private String diaryContent;

    @Column(name = "diary_shared_couple_id")
    private Long diarySharedCoupleId;

    @Version
    private Long version;

    @Builder(
            builderClassName = "DailyRecordSaveBuilder",
            builderMethodName = "DailyRecordSaveBuilder"
    )
    public DailyRecord(User user, LocalDate recordDate) {
        this.user = user;
        this.recordDate = recordDate;
    }


    public void updateMood(String mood) {
        this.mood = mood;
    }

    public void updateDiary(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public void deleteDiary() {
        this.diaryContent = null;
        this.diarySharedCoupleId = null;
    }

    public void updateDiarySharedCoupleId(Long diarySharedCoupleId) {
        this.diarySharedCoupleId = diarySharedCoupleId;
    }
}