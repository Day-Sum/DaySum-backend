package com.jung.daysum.domain;

import com.jung.daysum.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

@Table(name = "couple")
@Entity
public class Couple extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_user_id", nullable = false)
    private User firstUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_user_id", nullable = false)
    private User secondUser;

    @Column(name = "started_at", nullable = false)
    private LocalDate startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Builder(builderClassName = "CoupleSaveBuilder", builderMethodName = "CoupleSaveBuilder")
    public Couple(User firstUser, User secondUser, LocalDate startedAt) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.startedAt = startedAt;
    }

    public void updateStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public void disconnect() {
        this.endedAt = LocalDateTime.now();
    }
}