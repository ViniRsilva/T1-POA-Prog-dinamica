package com.ages.volunteersmile.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.ages.volunteersmile.application.dto.*;

public interface VisitService {
    VisitDTO createVisit(CreateVisitDTO dto);
    List<VisitDTO> listAll();
    List<VisitDTO> listByDay(LocalDate date);
    List<VisitMonthDTO> listByMonth(LocalDate anyDateInMonth);
    FeedbackDTO addVolunteerFeedback(UserFeedbackDTO dto);
    FeedbackDTO getLastVolunteerFeedback(UUID roomId);
    List<FeedbackDTO> getAllFeedbacksByRoom(UUID roomId);
    VisitTimeDTO endVisitById(UUID visitId);
    VisitDTO startVisitById(UUID visitId);
    VisitDTO getNextVisitByVolunteer(UUID volunteerId);
}
