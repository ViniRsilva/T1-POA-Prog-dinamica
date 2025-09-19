package com.ages.volunteersmile.application.service;

import java.util.List;

import com.ages.volunteersmile.application.dto.CreateVisitDTO;
import com.ages.volunteersmile.application.dto.VisitDTO;

public interface VisitService {
    VisitDTO createVisit(CreateVisitDTO dto);
    List<VisitDTO> listAll();
    VisitDTO getNextVisitByVolunteer(UUID volunteerId);
}
