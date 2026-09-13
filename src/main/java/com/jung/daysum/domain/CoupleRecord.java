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
@Entity
public class CoupleRecord extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", nullable = false)
    private Couple couple;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "updated_by_user_id", nullable = false)
    private Long updatedByUserId;

    @Version
    private Long version;


    @Builder(
            builderClassName = "CoupleRecordSaveBuilder",
            builderMethodName = "CoupleRecordSaveBuilder"
    )
    public CoupleRecord(
            Couple couple,
            LocalDate recordDate,
            String content,
            Long updatedByUserId
    ) {
        this.couple = couple;
        this.recordDate = recordDate;
        this.content = content;
        this.updatedByUserId = updatedByUserId;
    }


    public void updateRecord(
            String content,
            Long updatedByUserId
    ) {
        this.content = content;
        this.updatedByUserId = updatedByUserId;
    }
}