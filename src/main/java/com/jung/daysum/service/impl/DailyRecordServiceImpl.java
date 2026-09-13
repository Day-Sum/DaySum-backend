package com.jung.daysum.service.impl;

import com.jung.daysum.domain.Couple;
import com.jung.daysum.domain.DailyRecord;
import com.jung.daysum.domain.User;
import com.jung.daysum.dto.DailyRecordDto;
import com.jung.daysum.repository.DailyRecordRepository;
import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.response.exeption.Exception404;
import com.jung.daysum.service.CoupleService;
import com.jung.daysum.service.DailyRecordService;
import com.jung.daysum.service.StorageService;
import com.jung.daysum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.jung.daysum.dto.MusicDto;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyRecordServiceImpl implements DailyRecordService {

    private final UserService userService;
    private final CoupleService coupleService;
    private final DailyRecordRepository dailyRecordRepository;
    private final StorageService storageService;

    @Transactional
    @Override
    public DailyRecordDto.MoodResponse updateTodayMood(
            DailyRecordDto.MoodUpdateRequest moodUpdateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(moodUpdateRequestDto.getMood() == null ||
                moodUpdateRequestDto.getMood().isBlank()) {
            throw new Exception400.DailyRecordBadRequest(
                    "기분이 입력되지 않았습니다.");
        }

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(loginUser.getId(), recordDate)
                .orElse(null);

        if(dailyRecord == null) {
            dailyRecord = DailyRecord.DailyRecordSaveBuilder()
                    .user(loginUser)
                    .recordDate(recordDate)
                    .build();

            dailyRecord.updateMood(moodUpdateRequestDto.getMood());
            dailyRecordRepository.save(dailyRecord);
        }
        else {
            dailyRecord.updateMood(moodUpdateRequestDto.getMood());
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.MoodResponse.builder()
                .mood(dailyRecord.getMood())
                .version(dailyRecord.getVersion())
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public DailyRecordDto.Response findDailyRecordByDate(
            LocalDate recordDate
    ) {
        User loginUser = userService.findLoginUser();

        DailyRecord dailyRecord =
                findDailyRecord(loginUser.getId(), recordDate);

        DailyRecordDto.Response dailyRecordResponseDto =
                new DailyRecordDto.Response(dailyRecord);

        return dailyRecordResponseDto;
    }


    @Transactional
    @Override
    public DailyRecordDto.DiaryResponse updateDiary(
            LocalDate recordDate,
            DailyRecordDto.DiaryUpdateRequest diaryUpdateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(diaryUpdateRequestDto.getDiaryContent() == null ||
                diaryUpdateRequestDto.getDiaryContent().isBlank()) {
            throw new Exception400.DailyRecordBadRequest(
                    "일기 내용이 입력되지 않았습니다.");
        }

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(loginUser.getId(), recordDate)
                .orElse(null);

        if(dailyRecord == null) {
            dailyRecord = DailyRecord.DailyRecordSaveBuilder()
                    .user(loginUser)
                    .recordDate(recordDate)
                    .build();

            dailyRecord.updateDiary(diaryUpdateRequestDto.getDiaryContent());
            dailyRecordRepository.save(dailyRecord);
        }
        else {
            dailyRecord.updateDiary(diaryUpdateRequestDto.getDiaryContent());
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.DiaryResponse.builder()
                .diaryContent(dailyRecord.getDiaryContent())
                .version(dailyRecord.getVersion())
                .build();
    }


    @Transactional
    @Override
    public void deleteDiary(LocalDate recordDate) {
        User loginUser = userService.findLoginUser();

        DailyRecord dailyRecord =
                findDailyRecord(loginUser.getId(), recordDate);

        dailyRecord.deleteDiary();
    }


    @Transactional
    @Override
    public DailyRecordDto.DiaryShareResponse updateDiaryShare(
            LocalDate recordDate,
            DailyRecordDto.DiaryShareRequest diaryShareRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(diaryShareRequestDto.getShared() == null) {
            throw new Exception400.DailyRecordBadRequest(
                    "일기 공유 여부가 입력되지 않았습니다.");
        }

        DailyRecord dailyRecord =
                findDailyRecord(loginUser.getId(), recordDate);

        if(diaryShareRequestDto.getShared() == true) {
            if(dailyRecord.getDiaryContent() == null ||
                    dailyRecord.getDiaryContent().isBlank()) {
                throw new Exception400.DailyRecordBadRequest(
                        "공유할 일기가 존재하지 않습니다.");
            }

            Couple couple =
                    coupleService.findCoupleWithUserId(loginUser.getId());

            dailyRecord.updateDiarySharedCoupleId(couple.getId());
        }
        else {
            dailyRecord.updateDiarySharedCoupleId(null);
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.DiaryShareResponse.builder()
                .shared(dailyRecord.getDiarySharedCoupleId() != null)
                .version(dailyRecord.getVersion())
                .build();
    }

    @Transactional
    @Override
    public DailyRecordDto.PhotoResponse updateTodayPhoto(
            MultipartFile imageFile
    ) throws IOException {

        User loginUser = userService.findLoginUser();

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(
                        loginUser.getId(),
                        recordDate
                )
                .orElse(null);

        String uploadObjectKey =
                storageService.uploadImage(
                        imageFile,
                        "photos"
                );

        if(dailyRecord == null) {
            dailyRecord = DailyRecord.DailyRecordSaveBuilder()
                    .user(loginUser)
                    .recordDate(recordDate)
                    .build();

            dailyRecord.updatePhotoObjectKey(uploadObjectKey);

            dailyRecordRepository.save(dailyRecord);
        }
        else {
            if(dailyRecord.getPhotoObjectKey() != null) {
                storageService.deleteImage(
                        dailyRecord.getPhotoObjectKey()
                );
            }

            dailyRecord.updatePhotoObjectKey(uploadObjectKey);
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.PhotoResponse.builder()
                .photoObjectKey(
                        dailyRecord.getPhotoObjectKey()
                )
                .version(dailyRecord.getVersion())
                .build();
    }

    @Transactional
    @Override
    public void deleteTodayPhoto() {

        User loginUser = userService.findLoginUser();

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord =
                findDailyRecord(
                        loginUser.getId(),
                        recordDate
                );

        if(dailyRecord.getPhotoObjectKey() == null) {
            throw new Exception400.DailyRecordBadRequest(
                    "삭제할 사진이 존재하지 않습니다."
            );
        }

        storageService.deleteImage(
                dailyRecord.getPhotoObjectKey()
        );

        dailyRecord.deletePhoto();
    }

    @Transactional
    @Override
    public DailyRecordDto.DrawingResponse updateTodayDrawing(
            MultipartFile imageFile
    ) throws IOException {

        User loginUser = userService.findLoginUser();

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(
                        loginUser.getId(),
                        recordDate
                )
                .orElse(null);

        String uploadObjectKey =
                storageService.uploadImage(
                        imageFile,
                        "drawings"
                );

        if(dailyRecord == null) {
            dailyRecord = DailyRecord.DailyRecordSaveBuilder()
                    .user(loginUser)
                    .recordDate(recordDate)
                    .build();

            dailyRecord.updateDrawingObjectKey(
                    uploadObjectKey
            );

            dailyRecordRepository.save(dailyRecord);
        }
        else {
            if(dailyRecord.getDrawingObjectKey() != null) {
                storageService.deleteImage(
                        dailyRecord.getDrawingObjectKey()
                );
            }

            dailyRecord.updateDrawingObjectKey(
                    uploadObjectKey
            );
        }

        dailyRecordRepository.flush();

        return DailyRecordDto.DrawingResponse.builder()
                .drawingObjectKey(
                        dailyRecord.getDrawingObjectKey()
                )
                .version(dailyRecord.getVersion())
                .build();
    }

    @Transactional
    @Override
    public void deleteTodayDrawing() {

        User loginUser = userService.findLoginUser();

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord =
                findDailyRecord(
                        loginUser.getId(),
                        recordDate
                );

        if(dailyRecord.getDrawingObjectKey() == null) {
            throw new Exception400.DailyRecordBadRequest(
                    "삭제할 그림이 존재하지 않습니다."
            );
        }

        storageService.deleteImage(
                dailyRecord.getDrawingObjectKey()
        );

        dailyRecord.deleteDrawing();
    }

    @Transactional
    @Override
    public MusicDto.Response updateTodayMusic(
            MusicDto.UpdateRequest musicUpdateRequestDto
    ) {
        User loginUser = userService.findLoginUser();

        if(musicUpdateRequestDto.getTrackId() == null ||
                musicUpdateRequestDto.getTrackId().isBlank()) {
            throw new Exception400.MusicBadRequest(
                    "음악 trackId가 입력되지 않았습니다."
            );
        }

        if(musicUpdateRequestDto.getTitle() == null ||
                musicUpdateRequestDto.getTitle().isBlank()) {
            throw new Exception400.MusicBadRequest(
                    "음악 제목이 입력되지 않았습니다."
            );
        }

        if(musicUpdateRequestDto.getArtist() == null ||
                musicUpdateRequestDto.getArtist().isBlank()) {
            throw new Exception400.MusicBadRequest(
                    "음악 가수가 입력되지 않았습니다."
            );
        }

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord = dailyRecordRepository
                .findByUser_IdAndRecordDate(
                        loginUser.getId(),
                        recordDate
                )
                .orElse(null);

        if(dailyRecord == null) {
            dailyRecord = DailyRecord.DailyRecordSaveBuilder()
                    .user(loginUser)
                    .recordDate(recordDate)
                    .build();

            dailyRecord.updateMusic(
                    musicUpdateRequestDto.getProvider(),
                    musicUpdateRequestDto.getTrackId(),
                    musicUpdateRequestDto.getTitle(),
                    musicUpdateRequestDto.getArtist(),
                    musicUpdateRequestDto.getArtworkUrl(),
                    musicUpdateRequestDto.getStoreUrl()
            );

            dailyRecordRepository.save(dailyRecord);
        }
        else {
            dailyRecord.updateMusic(
                    musicUpdateRequestDto.getProvider(),
                    musicUpdateRequestDto.getTrackId(),
                    musicUpdateRequestDto.getTitle(),
                    musicUpdateRequestDto.getArtist(),
                    musicUpdateRequestDto.getArtworkUrl(),
                    musicUpdateRequestDto.getStoreUrl()
            );
        }

        dailyRecordRepository.flush();

        return MusicDto.Response.builder()
                .provider(dailyRecord.getMusicProvider())
                .trackId(dailyRecord.getMusicTrackId())
                .title(dailyRecord.getMusicTitle())
                .artist(dailyRecord.getMusicArtist())
                .artworkUrl(dailyRecord.getMusicArtworkUrl())
                .storeUrl(dailyRecord.getMusicStoreUrl())
                .version(dailyRecord.getVersion())
                .build();
    }

    @Transactional
    @Override
    public void deleteTodayMusic() {
        User loginUser = userService.findLoginUser();

        LocalDate recordDate = LocalDate.now();

        DailyRecord dailyRecord =
                findDailyRecord(
                        loginUser.getId(),
                        recordDate
                );

        if(dailyRecord.getMusicTrackId() == null) {
            throw new Exception400.DailyRecordBadRequest(
                    "삭제할 음악이 존재하지 않습니다."
            );
        }

        dailyRecord.deleteMusic();
    }

    // ========== 유틸성 메소드 ========== //

    @Transactional(readOnly = true)
    @Override
    public DailyRecord findDailyRecord(
            Long userId,
            LocalDate recordDate
    ) {
        return dailyRecordRepository
                .findByUser_IdAndRecordDate(userId, recordDate)
                .orElseThrow(
                        () -> new Exception404.NoSuchDailyRecord(
                                String.format(
                                        "userId = %d, recordDate = %s",
                                        userId,
                                        recordDate
                                )
                        )
                );
    }
}