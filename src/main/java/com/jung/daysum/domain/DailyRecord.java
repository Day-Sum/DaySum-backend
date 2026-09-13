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

    @Column(name = "photo_object_key")
    private String photoObjectKey;

    @Column(name = "drawing_object_key")
    private String drawingObjectKey;

    @Column(name = "diary_content", columnDefinition = "TEXT")
    private String diaryContent;

    @Column(name = "diary_shared_couple_id")
    private Long diarySharedCoupleId;

    @Column(name = "music_provider")
    private String musicProvider;

    @Column(name = "music_track_id")
    private String musicTrackId;

    @Column(name = "music_title")
    private String musicTitle;

    @Column(name = "music_artist")
    private String musicArtist;

    @Column(name = "music_artwork_url")
    private String musicArtworkUrl;

    @Column(name = "music_store_url")
    private String musicStoreUrl;

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

    public void updateMusic(
            String musicProvider,
            String musicTrackId,
            String musicTitle,
            String musicArtist,
            String musicArtworkUrl,
            String musicStoreUrl
    ) {
        this.musicProvider = musicProvider;
        this.musicTrackId = musicTrackId;
        this.musicTitle = musicTitle;
        this.musicArtist = musicArtist;
        this.musicArtworkUrl = musicArtworkUrl;
        this.musicStoreUrl = musicStoreUrl;
    }

    public void deleteMusic() {
        this.musicProvider = null;
        this.musicTrackId = null;
        this.musicTitle = null;
        this.musicArtist = null;
        this.musicArtworkUrl = null;
        this.musicStoreUrl = null;
    }

    public void updateMood(String mood) {
        this.mood = mood;
    }

    public void updatePhotoObjectKey(String photoObjectKey) {
        this.photoObjectKey = photoObjectKey;
    }

    public void deletePhoto() {
        this.photoObjectKey = null;
    }

    public void updateDrawingObjectKey(String drawingObjectKey) {
        this.drawingObjectKey = drawingObjectKey;
    }

    public void deleteDrawing() {
        this.drawingObjectKey = null;
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