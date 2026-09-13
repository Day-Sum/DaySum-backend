package com.jung.daysum.domain;

import com.jung.daysum.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "activity")
@Entity
public class Activity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;


    @Builder(
            builderClassName = "ActivitySaveBuilder",
            builderMethodName = "ActivitySaveBuilder"
    )
    public Activity(
            User user,
            String content,
            LocalDateTime startedAt
    ) {
        this.user = user;
        this.content = content;
        this.startedAt = startedAt;
    }


    public void end() {
        this.endedAt = LocalDateTime.now();
    }
}