package com.jung.daysum.service;

import com.jung.daysum.dto.CoupleRecordDto;

import java.time.LocalDate;

public interface CoupleRecordService {

    CoupleRecordDto.Response findCoupleRecord(LocalDate date);

    CoupleRecordDto.Response updateCoupleRecord(LocalDate date, CoupleRecordDto.UpdateRequest coupleRecordUpdateRequestDto);

}