package com.ages.volunteersmile.application.service;

import java.time.LocalDate;
import java.util.List;

import com.ages.volunteersmile.application.dto.CreateVisitDTO;
import com.ages.volunteersmile.application.dto.VisitDTO;

public interface VisitService {
    VisitDTO createVisit(CreateVisitDTO dto);
    List<VisitDTO> listAll();
    List<VisitDTO> listByDay(LocalDate date);
    List<VisitDTO> listByMonth(LocalDate anyDateInMonth);
}
