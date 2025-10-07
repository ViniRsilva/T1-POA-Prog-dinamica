package com.ages.volunteersmile.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserVisitFeedbackDTO {
    private UUID userVisitId;
    private String feedback;
}

