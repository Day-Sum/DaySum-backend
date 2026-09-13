package com.jung.daysum.service;

import com.jung.daysum.domain.enums.CalendarScope;
import com.jung.daysum.dto.CalendarDto;

public interface CalendarService {

    CalendarDto.Response findCalendar(Integer year, Integer month, CalendarScope scope);
}