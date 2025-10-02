package com.ages.volunteersmile.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserVisitFeedbackDTO {
    private UUID userVisitId;
    private String feedback;
}

