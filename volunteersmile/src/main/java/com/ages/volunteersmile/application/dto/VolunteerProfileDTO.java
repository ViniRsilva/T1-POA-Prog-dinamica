package com.ages.volunteersmile.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VolunteerProfileDTO {
    private VolunteerDTO volunteer;
    private List<VisitWithHasFeedbackDTO> visits;

}
