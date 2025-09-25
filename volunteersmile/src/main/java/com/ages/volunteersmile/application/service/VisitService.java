package com.ages.volunteersmile.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.ages.volunteersmile.application.dto.CreateVisitDTO;
import com.ages.volunteersmile.application.dto.VisitDTO;
import com.ages.volunteersmile.application.dto.VisitMonthDTO;
import com.ages.volunteersmile.application.dto.VisitTimeDTO;

public interface VisitService {
    VisitDTO createVisit(CreateVisitDTO dto);
    List<VisitDTO> listAll();
    List<VisitDTO> listByDay(LocalDate date);
    List<VisitMonthDTO> listByMonth(LocalDate anyDateInMonth);
    VisitTimeDTO endVisitById(UUID visitId);
    VisitDTO startVisitById(UUID visitId);
    VisitDTO getNextVisitByVolunteer(UUID volunteerId);
}
